package cn.injava.es.spring.service;

import cn.injava.es.spring.domain.Document;
import cn.injava.es.spring.domain.Post;
import com.google.gson.Gson;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Green Lei on 2015/11/17 12:02.
 */
@Service
public class PostServiceImpl implements PostService{
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    Gson gson = new Gson();

    @Override
    public Post save(Document<Post> post) {
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(post.getDocument())
                .withIndexName(post.getIndexName())
                .withType(post.getTypeName()).build();

        elasticsearchTemplate.bulkIndex(Arrays.asList(indexQuery));
        elasticsearchTemplate.refresh("post-test-index", true);

        return post.getDocument();
    }

    @Override
    public List<Post> query(SearchQuery searchQuery) {
        List<Post> result = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<List<Post>>() {
            @Override
            public List<Post> extract(SearchResponse response) {
                List<Post> resultList = new ArrayList<Post>();
                SearchHit[] hits = response.getHits().getHits();

                for (SearchHit hit : hits) {
                    Post postResponse = gson.fromJson(hit.sourceAsString(), Post.class);
                    resultList.add(postResponse);
                }

                return resultList;
            }
        });

        return result;
    }
}
