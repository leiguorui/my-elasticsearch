package cn.injava.test;

import cn.injava.es.spring.domain.Document;
import cn.injava.es.spring.domain.Post;
import cn.injava.es.spring.domain.Tag;
import cn.injava.es.spring.service.PostService;
import com.google.gson.Gson;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.jackson.core.JsonEncoding;
import org.elasticsearch.common.jackson.core.JsonFactory;
import org.elasticsearch.common.jackson.core.JsonGenerator;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.aggregations.Aggregations;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:elasticsearch.xml" })
public class PostServiceImplTest{
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private PostService postService;

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
        List<Post> posts = postService.query(searchQuery);

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

        postService.save(document);

    }
}