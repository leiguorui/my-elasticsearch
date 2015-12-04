package cn.injava.test;

import cn.injava.es.spring.dao.LogDao;
import cn.injava.es.spring.domain.Document;
import cn.injava.es.spring.domain.Log;
import cn.injava.es.spring.domain.Post;
import cn.injava.es.spring.domain.Tag;
import cn.injava.es.spring.service.ElasticSearchService;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:SpringConfig.xml" })
public class PostServiceImplTest{
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ElasticSearchService esService;

    @Autowired
    private LogDao logDao;

    @Before
    public void before() {
//        elasticsearchTemplate.deleteIndex(Post.class);
//        elasticsearchTemplate.createIndex(Post.class);
//        elasticsearchTemplate.putMapping(Post.class);
//        elasticsearchTemplate.refresh(Post.class, true);
    }

    @Test
    public void testTestplateQuery() throws Exception {
//        BoolQueryBuilder builder = boolQuery();
//        builder.must(nestedQuery("manuscripts", termQuery("manuscripts.status", "ACCEPTED")))
//                .must(nestedQuery("manuscripts.role", termQuery("manuscripts.role.name", "role3")));
//
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(builder).build();

        // given
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withIndices("post-test-index").withTypes("post-test-type")
                .addAggregation(terms("Bigining").field("title"))
                .withPageable(new PageRequest(0, 2))
                .build();
        // when
        List<Post> posts = esService.query(searchQuery);

    }

    @Test
    public void testTestplateSave() throws Exception {
        Tag tag = new Tag();
        tag.setId("1");
        tag.setName("tech");
        Tag tag2 = new Tag();
        tag2.setId("2");
        tag2.setName("elasticsearch");

        Post post = new Post();
        post.setId("3");
        post.setTitle("Bigining with spring boot application and elasticsearch");
        post.setTags(Arrays.asList(tag, tag2));

        Document<Post> document = new Document<Post>();
        document.setIndexName("test-index");
        document.setTypeName("test-type");
        document.setDocument(post);

        esService.save(document);
    }

    @Test
    public void testIntexTemplate() throws Exception {
        esService.setIndexTemplate(null);
    }

    @Test
    public void testLogSave() throws Exception {
        String nowDate = new DateTime().toString("yyyy-MM-dd");
        String indexName = "sirius_log_"+nowDate;
        String typeName = "user_action";

        List<Log> result =  logDao.getLogByLimit(1, 10);
        System.out.println(result.size());

        for (Log log : result){
            Document<Log> document = new Document<>();
            document.setIndexName(indexName);
            document.setTypeName(typeName);
            document.setDocument(log);

            esService.saveLog(document);
        }
    }
}