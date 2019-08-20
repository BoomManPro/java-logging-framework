## 配置文件

### 如何放,放在哪里 ?

Spring Boot的核心就是简化配置,约定优于配置。但是如何查找到相关配置呢?

推荐阅读[官方文档](https://docs.spring.io/spring-boot/docs/2.1.0.RELEASE/reference/htmlsingle/#boot-features-custom-log-configuration)
logback: logback-spring.xml, logback-spring.groovy, logback.xml, or logback.groovy

log4j2: log4j2-spring.xml or log4j2.xml

也可以参照相关代码:

log4j2-config.png

logback-config.png

### Spring Boot 推荐配置方式 ?

When possible, we recommend that you use the -spring variants for your logging configuration (for example, logback-spring.xml rather than logback.xml). If you use standard configuration locations, Spring cannot completely control log initialization.


## Spring Boot日志

Spring Boot项目有哪些日志 可以打那些日志

tomcat的日志有哪些 

access log

等


spring boot  debug日志 可以通过那些方式控制



如何快速定位错误 如何更清晰打印出日志


1.日志框架为何能在控制台输出信息

2.spring boot是如何把日志整合在一起的

3.SLF4J 的相关整合

## SLF4J和其他日志框架的整合

首先作为日志接口的Slf4j和日志系统之间是如何整合的 ?

以下以slf4j整合logback举例





















## slf4j + logback是如何绑定的

```java
1.  private static final Logger log = LoggerFactory.getLogger(SpringBootLogbackApplication.class);

2. 查看LoggerFactory.getLogger()方法
    public static Logger getLogger(Class<?> clazz) {
        // 获取Logger对象
        Logger logger = getLogger(clazz.getName());
        if (DETECT_LOGGER_NAME_MISMATCH) {
            Class<?> autoComputedCallingClass = Util.getCallingClass();
            if (autoComputedCallingClass != null && nonMatchingClasses(clazz, autoComputedCallingClass)) {
                Util.report(String.format("Detected logger name mismatch. Given name: \"%s\"; computed name: \"%s\".", logger.getName(),
                                autoComputedCallingClass.getName()));
                Util.report("See " + LOGGER_NAME_MISMATCH_URL + " for an explanation");
            }
        }
        return logger;
    }
    
3. 继续跟进 getLogger()
    /**
     * Return a logger named according to the name parameter using the
     * statically bound {@link ILoggerFactory} instance.
     * 
     * @param name
     *            The name of the logger.
     * @return logger
     */
    public static Logger getLogger(String name) {
        // 获取日志工厂
        ILoggerFactory iLoggerFactory = getILoggerFactory();
        // 返回日志对象 
        return iLoggerFactory.getLogger(name);
    }
    
4. 获取工厂实例
    /**
     * Return the {@link ILoggerFactory} instance in use.
     * <p/>
     * <p/>
     * ILoggerFactory instance is bound with this class at compile time.  
     * 编译时绑定工厂实例
     * 
     * @return the ILoggerFactory instance in use
     */
    public static ILoggerFactory getILoggerFactory() {
        // 没有初始化情况
        // 双重检测锁
        if (INITIALIZATION_STATE == UNINITIALIZED) {
            synchronized (LoggerFactory.class) {
                if (INITIALIZATION_STATE == UNINITIALIZED) {
                    // 初始化
                    INITIALIZATION_STATE = ONGOING_INITIALIZATION;
                    performInitialization();
                }
            }
        }
        switch (INITIALIZATION_STATE) {
        case SUCCESSFUL_INITIALIZATION:
            return StaticLoggerBinder.getSingleton().getLoggerFactory();
        case NOP_FALLBACK_INITIALIZATION:
            return NOP_FALLBACK_FACTORY;
        case FAILED_INITIALIZATION:
            throw new IllegalStateException(UNSUCCESSFUL_INIT_MSG);
        case ONGOING_INITIALIZATION:
            // support re-entrant behavior.
            // See also http://jira.qos.ch/browse/SLF4J-97
            return SUBST_FACTORY;
        }
        throw new IllegalStateException("Unreachable code");
    }
    
5. 初始化
    private final static void performInitialization() {
        bind();
        if (INITIALIZATION_STATE == SUCCESSFUL_INITIALIZATION) {
            versionSanityCheck();
        }
    }
    
6. 绑定
    private final static void bind() {
        try {
            Set<URL> staticLoggerBinderPathSet = null;
            // skip check under android, see also
            // http://jira.qos.ch/browse/SLF4J-328
            if (!isAndroid()) {
                staticLoggerBinderPathSet = findPossibleStaticLoggerBinderPathSet();
                reportMultipleBindingAmbiguity(staticLoggerBinderPathSet);
            }
            // the next line does the binding
            // 真正的绑定，将具体的实现绑定到slf4j
            StaticLoggerBinder.getSingleton();
            INITIALIZATION_STATE = SUCCESSFUL_INITIALIZATION;
            reportActualBinding(staticLoggerBinderPathSet);
            fixSubstituteLoggers();
            replayEvents();
            // release all resources in SUBST_FACTORY
            SUBST_FACTORY.clear();
         }
    }


7. logback-classic: org.slf4j.impl.StaticLoggerBinder
    public static StaticLoggerBinder getSingleton() {
        return SINGLETON;
    }

由此可以看出slf4j在编译时就找了具体的日志实现了，也就是 org.slf4j.impl.StaticLoggerBinder。
```