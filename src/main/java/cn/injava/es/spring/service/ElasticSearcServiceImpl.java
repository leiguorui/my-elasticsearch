package cn.injava.es.spring.service;

import cn.injava.es.spring.domain.Document;
import cn.injava.es.spring.domain.Log;
import cn.injava.es.spring.domain.Post;
import cn.injava.es.spring.utils.CustomResourceLoader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Green Lei on 2015/11/17 12:02.
 */
@Service
public class ElasticSearcServiceImpl implements ElasticSearchService {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CustomResourceLoader resourceLoader;

    @Value( "${es.url}" )
    private String esHost;

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

    /**
     * 保存日志
     *
     * @param log
     * @return
     */
    @Override
    public Log saveLog(Document<Log> log) {
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(log.getDocument())
                .withIndexName(log.getIndexName())
                .withType(log.getTypeName()).build();

        elasticsearchTemplate.bulkIndex(Arrays.asList(indexQuery));
        elasticsearchTemplate.refresh(log.getIndexName(), true);

        return log.getDocument();
    }

    /**
     * 设置index的template
     *
     * @param template
     * @return
     */
    @Override
    public void setIndexTemplate(JsonObject template) throws IOException {
        String indexTemplate = resourceLoader.getResourceData("classpath:/es/template/index_template_1.7.2.json");

        restTemplate.put(esHost+"/_template/sirius_logs_per_index", indexTemplate);
    }
}
