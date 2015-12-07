package cn.injava.es.spring.listener;

import cn.injava.es.spring.domain.Document;
import cn.injava.es.spring.domain.Log;
import cn.injava.es.spring.service.ElasticSearchService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Component;
import redis.clients.jedis.exceptions.JedisConnectionException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 从redis中获取user action数据，保存到es中
 *
 * Created by Green Lei on 2015/8/7 11:50.
 */
@Component
@Scope("prototype")
public class SaveUserActionTask{

    @Resource(name="redisTemplate")
    private ListOperations<String, String> listOps;

    @Value("${redis.pop.time}")
    private long time;

    @Value("${sirius_redis_log_user_action_es_key}")
    private String userActionKay;

    @Autowired
    private ElasticSearchService esService;

    private final static Logger logger = LoggerFactory.getLogger(SaveUserActionTask.class);

    Gson gson = new Gson();

    public void run() {
        try {
            //获取redis中的值
            List<String> value = listOps.range(userActionKay, 0, 0);

            if(value.size() > 0 ){
                //添加日志到数据库中
                int result = addLog(value.get(0));

                //如果插入成功,则删除redis中的值
                if(result > 0){
                    listOps.leftPop(userActionKay, time, TimeUnit.SECONDS);
                }
            }else {
                //如果没有数据,停止10分钟再取
                Thread.sleep(1000*60*10);
            }
        } catch(RedisConnectionFailureException | JedisConnectionException e) {
            //为了不要一直重新连接，暂停60分钟
            try {
                Thread.sleep(1000*60*60);
            } catch (InterruptedException ie) {
                logger.error(e.getMessage(), e);
                // handle the exception...
                Thread.currentThread().interrupt();
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * 插入日志到数据库
     * @param logMsg
     * @return
     */
    public int addLog(String logMsg){
        int result = -1;
        JsonObject userAction = gson.fromJson(logMsg, JsonObject.class);
        String params = userAction.get("params").toString();
        String cookies = userAction.get("cookies").toString();
        String run_environment = userAction.get("run_environment").toString();

        //将jsonobject转为string
        userAction.remove("params");
        userAction.addProperty("params", params);
        userAction.remove("cookies");
        userAction.addProperty("cookies", cookies);
        userAction.remove("run_environment");
        userAction.addProperty("run_environment", run_environment);

        Log userActionPo = gson.fromJson(userAction, Log.class);
        userActionPo.setCreate_time(new Date());
        try {
            String nowDate = new DateTime().toString("yyyy-MM-dd");
            String indexName = "sirius_log_"+nowDate;
            String typeName = "user_action";
            Document<Log> document = new Document<>();
            document.setIndexName(indexName);
            document.setTypeName(typeName);
            document.setDocument(userActionPo);

            esService.saveLog(document);
            logger.info("insert a item to database: {}", userActionPo.toString());

            result = 1;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("insert error: {}", e.toString());
        }

        return result;
    }

    @PostConstruct
    private void init() {
        logger.info("已启动1个线程："+userActionKay+"");
    }
}
