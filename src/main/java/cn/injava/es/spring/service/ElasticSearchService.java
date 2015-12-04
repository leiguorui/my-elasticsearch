package cn.injava.es.spring.service;

import cn.injava.es.spring.domain.Document;
import cn.injava.es.spring.domain.Log;
import cn.injava.es.spring.domain.Post;
import com.google.gson.JsonObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.io.IOException;
import java.util.List;

/**
 * Elasticsearch的操作
 *
 * Created by Green Lei on 2015/11/17 12:01.
 */
public interface ElasticSearchService {
    Post save(Document<Post> post);

    /**
     * 保存日志
     * @param log
     * @return
     */
    Log saveLog(Document<Log> log);

    /**
     * 设置index的template
     * @param template
     * @return
     */
    void setIndexTemplate(JsonObject template) throws IOException;

    List<Post> query(SearchQuery searchQuery);
}