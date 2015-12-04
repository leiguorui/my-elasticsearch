package cn.injava.es.spring.dao;

import cn.injava.es.spring.domain.Log;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Green Lei on 2015/12/4 10:38.
 */
public interface LogDao {
    List<Log> getLogByLimit(@Param("from") int from, @Param("to") int to);
}
