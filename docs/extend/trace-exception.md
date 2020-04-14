## 在项目中如何跟踪异常信息

Tencent 接口一般会有RequestId,能否借鉴

ThreadLocal + Interceptor 进行处理

## 前端日志

后台帮助前端去搭建一个前端异常处理


## Java服务挂了

我们需要对java服务做监控

Spring boot Admin 

Github Link:[spring-boot-admin-sample](https://github.com/BoomManPro/spring-boot-admin-sample.git)

只要是单机就会出现问题,搭建集群

## Java日志清理策略

subscription 打印80g，内存爆掉了。是不是需要一个清理策略

按照体积去分割 按照数量去限制

配置 maxHistory

## 日志的格式

统一的日志格式

## 查看错误 异常 的方法

CTRL + R  搜索历史命令

linux 命令的使用示例

linux中常见命令

内存使用使用

free -h

磁盘使用情况

df -lh

快速找出大文件占用

find . -type f -size +800M  -print0 | xargs -0 du -h | sort -nr



## application.yml

application.yml 与 xml 配置

spring boot debug  为什么就起作用了? 实际将具体包的级别变为debug 把日志加载的级别变为debug即可


## MongoDB  ElasticSearch Mysql

在使用过程中如何找到发送的请求

jpa show -sql true

HTTP的方式 直接打印出来

logger name 配置成包名即可 实战一下。

当我们在开发过程中遇到了一些关于依赖的bug 可以如上处理


## JPA 打印sql参数内容

事例 当将项目部署到目标服务器后，执行查询没有结果

```
    <logger name="org.hibernate.type.descriptor.sql.BasicBinder" level="trace" additivity="false">
        <appender-ref ref="CONSOLE"/>
    </logger>
```



## 类似SLF4J的功能 输出格式化


**数字填充0**
String.format()
```java
String.format("%03d", i)
```


## 日志信息搜索

linux 搜索日志  grep


## 相关插件推荐

Atl + m

String Manipulation //  Alt+m  字符串日常开发中经常用到的，但是不同的字符串类型在不同的地方可能有一些不同的规则，比如类名要用驼峰形式、常量需要全部大写等，有时候还需要进行编码解码等。这里推荐一款强大的字符串转换工具——String Manipulation


Maven Helper

让maven自动处理依赖冲突，还是我们自己解决

## 日志输出格式

使用参数化形式{}占位，[] 进行参数隔离
log.debug("Save order with order no：[{}], and order amount：[{}]");