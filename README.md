# elasticsearch example

---

Using Java API

To connect to the locally installed ES server,

> * create client
> * create index and set settings and mappings for document type
> * add documents to the index
> * get document
> * query document
> * delete document

#### elasticsearch 集成ik中文分词器

1.  http://my.oschina.net/xiaohui249/blog/232784
2.	http://www.searchtech.pro/articles/2013/02/18/1361190717673.html

####    保存日志的ES的Index Schema 的设计

1.   For log data, it is often intuitive to partition the data into indices based on a time interval such as daily or hourly.
+   Data expiration becomes very easy. Instead of relying on a TTL or other expiration methods, old indices can simply be deleted altogethe.
+   If a query is only looking for documents from a certain time period, it can be limited to fewer indices instead of having to query an entire cluster.
+   Since the most recent index will likely be receiving the majority of the traffic, Elasticsearch will maintain a larger cache for this index, improving performance.

####   elasticsearch的常用操作

+   创建 mapping的template : curl -XPUT localhost:9200/_template/my_template -d MyTemplateJsonContent
+   如何测试分词器:curl -XGET 'localhost:9200/my_index/_analyze?analyzer=my_analyzer&pretty=true' -d 'aaaa'

####    参考文档
+   https://engineering.opendns.com/2015/05/05/elasticsearch-you-know-for-logs/
+   http://www.cubrid.org/blog/dev-platform/our-experience-creating-large-scale-log-search-system-using-elasticsearch/
