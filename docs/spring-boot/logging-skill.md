# 日志使用技巧

- Log打印请求跟踪<TraceId>

- Log打印请求跟踪时异步任务没有TraceId的问题

- 动态日志级别修改<SetLogLevel>

- 日志滚动设置<rollingPolicy>

- 单体项目 外部化刷新日志配置

## Log打印请求id


```java

/**
 * @author wangqimeng
 * @date 2020/3/16 10:35
 */
public class TraceConfig {

    public static final String TRACE_STRING = "traceId";

    private TraceConfig() {
    }
}

```

```java

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

/**
 * @author wangqimeng
 * @date 2020/3/16 11:20
 */
@Slf4j
public class TraceLogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("TraceLogFilter init success");

    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MDC.put(TraceConfig.TRACE_STRING, UUID.randomUUID().toString().replace("-", ""));
        chain.doFilter(request, response);
    }
}

```

控制台输出:

```xml
    <!-- Console 输出设置 -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%date{yyyy-MM-dd HH:mm:ss} [%X{traceId}]  | %highlight(%-5level) | %boldYellow(%thread) | %boldGreen(%logger) | %msg%n   </pattern>
            <charset>UTF-8</charset>
        </encoder>
    </appender>
```

前端处理:
``` java

import lombok.Data;
import org.slf4j.MDC;

/**
 * @author wangqimeng
 * @date 2020/3/16 9:17
 */
@Data
public class ResultVo<T> {

    private int code;

    private String showMsg;

    private String errorMsg;

    private T data;

    private String traceId;

    public ResultVo(int code, String showMsg, String errorMsg, T data) {
        this.code = code;
        this.showMsg = showMsg;
        this.errorMsg = errorMsg;
        this.data = data;
        this.traceId = MDC.get(TraceConfig.TRACE_STRING);
    }

    public static <T> ResultVo<T> success() {
        return new ResultVo<>(ResultCode.SUCCESS.getCode(), "", ResultCode.SUCCESS.getShowMsg(), null);
    }

    public static <T> ResultVo<T> success(T data) {
        return new ResultVo<>(ResultCode.SUCCESS.getCode(), "", ResultCode.SUCCESS.getShowMsg(), data);
    }

    public static <T> ResultVo<T> success(String showMsg, T data) {
        return new ResultVo<>(ResultCode.SUCCESS.getCode(), showMsg, ResultCode.SUCCESS.getShowMsg(), data);
    }

    public static <T> ResultVo<T> error(ResultCode code, String errorMeg, T data) {
        return new ResultVo<>(code.getCode(), code.getShowMsg(), errorMeg, data);
    }

}

```
ResultCode
```java
import lombok.Getter;

/**
 * @author wangqimeng
 * @date 2020/3/16 9:20
 */
@Getter
public enum ResultCode {

    /**
     * SUCCESS
     */
    SUCCESS(20000, "SUCCESS"),
    /**
     * Inner Error
     */
    INNER_ERROR(50000, "系统内部错误"),
    /**
     * Login Error
     */
    LOGIN_ERROR(50001, "登录失败,用户名或者密码错误!"),

    /**
     * 登录超时
     */
    LOGIN_TIMEOUT(50002, "登录会话失效"),

    /**
     * 会话最大限制
     */
    SESSION_MAX_ERROR(50003, "登录失败,当前登录用户超过限制,建议您修改密码!"),
    /**
     * 方法找不到
     */
    NO_HANDLER(40004, "请求匹配失败"),
    /**
     * 失败请求
     */
    BAD_REQUEST(40000, "请求失败"),
    ;

    private int code;

    private String showMsg;

    ResultCode(int code, String showMsg) {
        this.code = code;
        this.showMsg = showMsg;
    }
}

```
## @Async 异步任务TraceId打印

MdcTaskDecorator.java
```java
import java.util.Map;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

/**
 * @author wangqimeng
 * @date 2020/4/17 9:39
 */
public class MdcTaskDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        // Right now: Web thread context !
        // (Grab the current thread MDC data)
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        return () -> {
            try {
                // Right now: @Async thread context !
                // (Restore the Web thread context's MDC data)
                MDC.setContextMap(contextMap);
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}
```

AsyncConfiguration.java

```java
import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author wangqimeng
 * @date 2020/4/17 9:43
 */
@Configuration
public class AsyncConfiguration  {

    @Bean
    public Executor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("default-async-");
        executor.setMaxPoolSize(10);
        executor.setTaskDecorator(new MdcTaskDecorator());
        executor.setCorePoolSize(5);
        executor.initialize();
        return executor;
    }


}

```


## 动态日志级别修改

```java
    @GetMapping("changeLogLevelToInfo")
    public ResultVo<Boolean> changeLogLevelToInfo() {
        LoggerContext loggerContext = ((LoggerContext) LoggerFactory.getILoggerFactory());
        ch.qos.logback.classic.Logger root = loggerContext.getLogger("root");
        root.setLevel(Level.INFO);
        return ResultVo.success(String.format("修改日志为Info级别,status:[%s]", root.isInfoEnabled()), root.isInfoEnabled());
    }

    @GetMapping("changeLogLevelToDebug")
    public ResultVo<Boolean> changeLogLevelToDebug() {
        LoggerContext loggerContext = ((LoggerContext) LoggerFactory.getILoggerFactory());
        ch.qos.logback.classic.Logger root = loggerContext.getLogger("root");
        root.setLevel(Level.DEBUG);
        return ResultVo.success(String.format("修改日志为Info级别,status:[%s]", root.isDebugEnabled()), root.isInfoEnabled());
    }

    @GetMapping("doLog")
    public ResultVo<String> doLog() {
        log.info("info日志级别测试打印 date:[{}]", LocalDate.now());
        log.debug("debug日志级别测试打印 date:[{}]", LocalDate.now());
        return ResultVo.success();
    }
```

## 日志滚动设置

config 配置:

logback-spring.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>

<!-- For assistance related to logback-translator or configuration files in general,                    -->
<!--    please contact the logback user mailing list at http://www.qos.ch/mailman/listinfo/logback-user -->
<!-- For professional support please see http://www.qos.ch/shop/products/professionalSupport            -->
<configuration scan="true" scanPeriod="30 seconds">
    <!-- 引入平台公共的日志配置 配置了info和error-->
    <include resource="logging/logback-initial.xml"/>
</configuration>
```

logging/logging-base.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>

<!-- For assistance related to logback-translator or configuration files in general,                    -->
<!--    please contact the logback user mailing list at http://www.qos.ch/mailman/listinfo/logback-user -->
<!-- For professional support please see http://www.qos.ch/shop/products/professionalSupport            -->
<!-- Base logback configuration provided for compatibility with ecsplatform like Spring boot.           -->
<included>

    <!--应用名称-->
    <property name="web-app_name" value="tomato-admin"/>
    <!--日志字符集-->
    <property name="char_set_encoding" value="UTF-8"/>
    <!--日志根目录-->
    <property name="file_log_base_home" value="logs"/>
    <!--历史根目录-->
    <property name="file_log_app_history_home" value="${file_log_base_home}/history"/>

    <property name="logger_additivity" value="false"/>

    <!--文件日志通用的格式-->
    <property name="file_log_pattern" value="%date{yyyy-MM-dd HH:mm:ss} [%X{traceId}] [%X{nextId}] | %highlight(%-5level) | %boldYellow(%thread) | %boldGreen(%logger) | %msg%n   "/>
    <!--控制台输出格式-->
    <property name="CONSOLE_LOG_PATTERN"
              value="${CONSOLE_LOG_PATTERN:-%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(%-60.60logger{59}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"/>

    <!--spring 日志基础配置-->
    <include resource="org/springframework/boot/logging/logback/defaults.xml"/>
    <!--spring 控制台日志相关配置-->
    <include resource="org/springframework/boot/logging/logback/console-appender.xml"/>

    <!-- Console 输出设置 -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>${file_log_pattern}</pattern>
            <charset>UTF-8</charset>
        </encoder>
    </appender>
</included>
```

logging/logging-initial.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>

<!-- For assistance related to logback-translator or configuration files in general,                    -->
<!--    please contact the logback user mailing list at http://www.qos.ch/mailman/listinfo/logback-user -->
<!-- For professional support please see http://www.qos.ch/shop/products/professionalSupport            -->
<!-- Base logback configuration provided for compatibility with ecsplatform like Spring boot.           -->
<included>

    <!--初始化-预制的日志appender-->
    <include resource="logging/logback-base.xml"/>

    <appender name="infoFileOutput" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <File>${file_log_base_home}/info.log</File>
        <append>true</append>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${file_log_app_history_home}/info_%d{yyyy-MM-dd}.log</fileNamePattern>
        </rollingPolicy>
        <encoder>
            <pattern>info:${file_log_pattern}</pattern>
            <charset>${char_set_encoding}</charset>
        </encoder>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>INFO</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <appender name="errorFileOutput" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <append>true</append>
        <!--        滚动策略-->
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <!--日志文件输出的文件名-->
            <FileNamePattern>${file_log_app_history_home}/error_%d{yyyy-MM-dd}.%i.log</FileNamePattern>
            <!--日志文件保留天数-->
            <MaxHistory>30</MaxHistory>
            <!--日志文件最大的大小-->
            <MaxFileSize>5MB</MaxFileSize>
        </rollingPolicy>
        <encoder>
            <pattern>error:${file_log_pattern}</pattern>
            <charset>${char_set_encoding}</charset>
        </encoder>
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>ERROR</level>
        </filter>
    </appender>


    <springProfile name="default">
        <root level="INFO">
            <appender-ref ref="CONSOLE"/>
        </root>
    </springProfile>

    <springProfile name="dev">
        <root level="INFO">
            <appender-ref ref="CONSOLE"/>
            <appender-ref ref="infoFileOutput"/>
            <appender-ref ref="errorFileOutput"/>
        </root>

        <logger name="org.hibernate.type.descriptor.sql.BasicBinder" level="trace" additivity="false">
            <appender-ref ref="CONSOLE"/>
        </logger>
    </springProfile>


</included>
```



#### logback的additivity="false" 与root关系

它是 子Logger 是否继承 root的Logger 的 输出源（appender） 的标志位。

具体说，默认情况下子Logger会继承root的Logger的appender，也就是说子Logger会在root的Logger的appender里输出。

1.若是additivity设为false，则子Logger只会在自己的appender里输出，不会在root的logger的appender里输出（个人可以理解为additivity设为false后，子Logger会覆盖掉root的logger）。

2.若是additivity设为true，则子Logger不止会在自己的appender里输出，还会在root的logger的appender里输出


## 单体项目 外部化刷新日志配置

在项目代码中我们会写很多日志( debug,info,error)等。在项目正常运行的时候,我们不会打印debug级别的日志.一般项目上生产环境后是 info级别。
当发生错误,或者需要定位问题时,我们需要把日志级别调整为debug级别 or trace级别。方便更好定位问题。

[Logback Configuration](http://logback.qos.ch/manual/configuration.html)

1. 配置定时获取外部化配置信息

这里的include可以是file,resource,url等等. 所以如果我们配置成url,理论可以搞定集群化的问题。

```xml
<?xml version="1.0" encoding="UTF-8"?>

<!-- For assistance related to logback-translator or configuration files in general,                    -->
<!--    please contact the logback user mailing list at http://www.qos.ch/mailman/listinfo/logback-user -->
<!-- For professional support please see http://www.qos.ch/shop/products/professionalSupport            -->
<configuration scan="true" scanPeriod="1 seconds">
    <!-- 引入平台公共的日志配置 配置了info和error-->
    <include resource="logging/logback-initial.xml"/>
    <!---->
    <include optional="true" file="config/logging-config.xml"/>
</configuration>
```

如果使用springProfile来隔离不同环境(生产,测试,开发)等.

我们使用外部化的配置如 `<include optional="true" file="config/logging-config.xml"/>` 来reload配置.

注意配置 -> 基础配置不要有生产的配置,否则会打印多次.