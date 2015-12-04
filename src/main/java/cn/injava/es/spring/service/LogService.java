package cn.injava.es.spring.service;

import cn.injava.es.spring.domain.Log;

import java.util.List;

/**
 * Created by Green Lei on 2015/12/4 11:30.
 */
public interface LogService {

    /**
     * 获取分页获取日志
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<Log> getLogByPage(int pageNo, int pageSize);
}
