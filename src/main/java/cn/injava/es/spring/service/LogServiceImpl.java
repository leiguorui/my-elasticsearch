package cn.injava.es.spring.service;

import cn.injava.es.spring.dao.LogDao;
import cn.injava.es.spring.domain.Log;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Green Lei on 2015/12/4 11:30.
 */
public class LogServiceImpl implements LogService{
    @Autowired
    private LogDao logDao;

    /**
     * 获取分页获取日志
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public List<Log> getLogByPage(int pageNo, int pageSize) {
        return logDao.getLogByLimit((pageNo-1)*pageSize, pageSize);
    }
}
