# Spring Boot 集成日志框架


- Spring Boot 集成 Logback

- Spring Boot 集成 Log4j2

## 日志配置如何放,放在哪里 ?

Spring Boot的核心就是简化配置,约定优于配置。但是如何查找到相关配置呢?

### Spring Boot Docs




![日志体系](https://raw.githubusercontent.com/BoomManPro/java-logging-framework/master/docs/stastic/images/spring-boot-log-conf.png)

Link: [官方文档](https://docs.spring.io/spring-boot/docs/2.1.0.RELEASE/reference/htmlsingle/#boot-features-custom-log-configuration)

### log4j2-config
![日志体系](https://raw.githubusercontent.com/BoomManPro/java-logging-framework/master/docs/stastic/images/log4j2-config.png)
### logback-config
![日志体系](https://raw.githubusercontent.com/BoomManPro/java-logging-framework/master/docs/stastic/images/logback-config.png)




## Spring Boot 推荐配置方式 ?

When possible, we recommend that you use the -spring variants for your logging configuration (for example, logback-spring.xml rather than logback.xml). If you use standard configuration locations, Spring cannot completely control log initialization.


`简单来说就是推荐xml命名为 xxx-spring.xml `




## Spring Boot日志

**Spring Boot项目有哪些日志 可以打那些日志**

1.程序日志(使用logging框架打印)

2.容器日志Access Log (内嵌Tomcat日志) [Configure Access Logging 参考文档](https://docs.spring.io/spring-boot/docs/2.1.0.RELEASE/reference/htmlsingle/#howto-configure-accesslogs)

3.日志框架启动日志(一般级别为info||warn)



## spring boot 日志 可以通过那些方式控制

1.application.yml 中的logging.level

2.logging.config


## logger name的作用

如何Debug name=packageName
 
如何更清晰打印出日志(Trace)


## Root

Root 为兜底的,线上严禁设置为Debug 一般为Info


