package com.renrennet.es;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.fieldQuery;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * Created by leiguorui on 1/17/15.
 */
public class ElasticSearch {

    /**
     * 生成要插入elasticsearch的document
     * @param title
     * @param content
     * @param postDate
     * @param tags
     * @param author
     * @return
     */
    public static Map<String, Object> putJsonDocument(String title, String content, Date postDate,
                                                      String[] tags, String author){

        Map<String, Object> jsonDocument = new HashMap<String, Object>();

        jsonDocument.put("title", title);
        jsonDocument.put("conten", content);
        jsonDocument.put("postDate", postDate);
        jsonDocument.put("tags", tags);
        jsonDocument.put("author", author);

        return jsonDocument;
    }

    /**
     * 创建index，并插入document
     * @param client
     * @return
     */
    public static IndexResponse index(Client client){
        IndexResponse response = client.prepareIndex("kodcucom", "article", "1")
                .setSource(putJsonDocument("ElasticSearch: Java API",
                        "ElasticSearch provides the Java API, all operations "
                                + "can be executed asynchronously using a client object.",
                        new Date(),
                        new String[]{"elasticsearch"},
                        "Hüseyin Akdoğan")).execute().actionGet();

        return response;
    }

    /**
     * get document
     * @param client
     * @return
     */
    public static GetResponse getDocument(Client client){
        GetResponse getResponse = client.prepareGet("kodcucom", "article", "1").execute().actionGet();

        Map<String, Object> source = getResponse.getSource();

        System.out.println("------------getDocument start--------------");
        System.out.println("Index: " + getResponse.getIndex());
        System.out.println("Type: " + getResponse.getType());
        System.out.println("Id: " + getResponse.getId());
        System.out.println("Version: " + getResponse.getVersion());
        System.out.println(source);
        System.out.println("------------getDocument end---------------");

        return getResponse;
    }

    /**
     * search document
     * @param client
     * @param index
     * @param type
     * @param field
     * @param value
     * @return
     */
    public static SearchResponse searchDocument(Client client, String index, String type,
                                      String field, String value){

        SearchResponse response = client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.QUERY_AND_FETCH)
                .setQuery(fieldQuery(field, value))
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();

        SearchHit[] results = response.getHits().getHits();

        System.out.println("----------searchDocument start--------------");
        System.out.println("Current results: " + results.length);
        for (SearchHit hit : results) {
            Map<String,Object> result = hit.getSource();
            System.out.println(result);
        }
        System.out.println("----------searchDocument end--------------");
        return response;
    }

    /**
     * update document
     * @param client
     * @param index
     * @param type
     * @param id
     * @param field
     * @param newValue
     */
    public static void updateDocument(Client client, String index, String type,
                                      String id, String field, String newValue){

        Map<String, Object> updateObject = new HashMap<String, Object>();
        updateObject.put(field, newValue);

        client.prepareUpdate(index, type, id)
                .setScript("ctx._source." + field + "=" + field)
                .setScriptParams(updateObject).execute().actionGet();
    }

    /**
     * delete document
     * @param client
     * @param index
     * @param type
     * @param id
     */
    public static void deleteDocument(Client client, String index, String type, String id){

        DeleteResponse response = client.prepareDelete(index, type, id).execute().actionGet();
        System.out.println("Information on the deleted document:");
        System.out.println("Index: " + response.getIndex());
        System.out.println("Type: " + response.getType());
        System.out.println("Id: " + response.getId());
        System.out.println("Version: " + response.getVersion());
    }

    public static void main(String[] args){
        //这里创建了新node，会消耗较大的时间，不建议使用
//        Node node    = nodeBuilder().node();
//        Client client   = node.client();

        Client client = new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress("localhost", 9300));

        index(client);

        getDocument(client);

        updateDocument(client, "kodcucom", "article", "1", "tags", "big-data");

        searchDocument(client, "kodcucom", "article", "title", "ElasticSearch");

        deleteDocument(client, "kodcucom", "article", "1");

        client.close();
    }
}
