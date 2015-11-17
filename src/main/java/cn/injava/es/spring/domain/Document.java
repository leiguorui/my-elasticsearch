package cn.injava.es.spring.domain;

/**
 * Created by Green Lei on 2015/11/17 18:39.
 */
public class Document<T> {
    private String indexName;
    private String typeName;
    private T document;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public T getDocument() {
        return document;
    }

    public void setDocument(T document) {
        this.document = document;
    }
}
