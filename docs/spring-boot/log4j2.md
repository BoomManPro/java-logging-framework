## Log4j2


# Spring Boot + Log4j2

## Quick Start

### 引入依赖

```pom
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-log4j2</artifactId>
        </dependency>
```

### 移除冲突

```pom
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
            <exclusions>
                <exclusion>
                    <artifactId>spring-boot-starter-logging</artifactId>
                    <groupId>org.springframework.boot</groupId>
                </exclusion>
            </exclusions>
        </dependency>
```

## 依赖管理小插件

![Maven Helper](https://raw.githubusercontent.com/BoomManPro/java-logging-framework/master/docs/stastic/images/maven-helper.png)

### Link

[log4j2 官网](http://logging.apache.org/log4j/2.x/)