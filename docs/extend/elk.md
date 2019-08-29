##


## Windows docker 安装基础

安装ElasticSearch 

安装Kibana

安装LogStash

docker 容器之间通信

docker 挂载文件




windows docker 

logback ElasticSearch LogStash Kibana 集成

涉及到一些docker命令

查看容器使用状态

docker stats

docker ps

docker images

docker logs -f 

docker inspect | grep

docker inspect | findstr

docker rm 

docker run 

docker stop

关于logback的一些设置 udp tcp logstash

关于logstash 的输出配置 参考文档等


## ElasticSearch

elasticsearch
```
docker run -it --name elasticsearch -d -p 9200:9200 -p 9300:9300 -p 5601:5601 elasticsearch
```

## LogStash

```logstash.config
input
{
		tcp
		{
			port => 8888
			codec => json
		}
}
 
output
{
	   elasticsearch{
			hosts => "172.17.0.2:9200"
			index => "indextest"
		}
 
		stdout{
			codec => rubydebug
		}
}
```

```
docker run  -p 8888:8888 -d -v /c/Users/BoomManPro/docker/logstash:/docker/logstash logstash -f /docker/logstash/logstash.config
```

