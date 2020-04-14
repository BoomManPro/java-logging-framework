# 日志使用技巧

- Log打印请求跟踪<TraceId>

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