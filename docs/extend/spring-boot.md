## Spring Boot和内嵌Tomcat之间的关系




外置tomcat可以灵活配置，特别针对并发量、性能、缓存


多个项目占用内存空间的问题

内嵌Tomcat也可以打印一些信息 `access log`

```yaml
server:
  tomcat:
    accesslog:
      enabled: true
      pattern: "%t %a %r %s (%D ms)"
    basedir: ./data/accesslogs/${spring.application.name}
```