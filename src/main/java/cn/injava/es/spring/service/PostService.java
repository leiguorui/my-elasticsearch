package cn.injava.es.spring.service;

import cn.injava.es.spring.domain.Document;
import cn.injava.es.spring.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.List;

/**
 * Created by Green Lei on 2015/11/17 12:01.
 */
public interface PostService {
    Post save(Document<Post> post);

    List<Post> query(SearchQuery searchQuery);
}