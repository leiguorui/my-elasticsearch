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

####    elasticsearch集成kibana

kibana是一个数据可视化的工具,我们用来分析日志,学习kibana可以参考Kibana Essentials.pdf,下面是es集成kibana的步骤:

+   下载elasticsearch 1.7.2和 kibana 4.1.3,(elasticsearch2.1.0需要kibana5.0.0)
+   安装elasticsearch, 修改kibana的配置文件kibana.yml
+   启动kibana后,在setting页面里配置elasticsearch里的index,然后在discover页面里就可以看到数据了

####    参考文档
+   https://engineering.opendns.com/2015/05/05/elasticsearch-you-know-for-logs/
+   http://www.cubrid.org/blog/dev-platform/our-experience-creating-large-scale-log-search-system-using-elasticsearch/
