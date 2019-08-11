# 关于日志框架的小问题

1. Java日志框架有哪些
2. Java自身有没有日志框架
3. 常见的日志框架有哪些
4. 他们之间的关系是什么
5. Spring Boot 集成各类框架
6. 门面设计模式与日志框架
7. 日志可以输出到哪里
8. 如何配置及日志框架进阶



## Java的日志框架

java.util.logging.Logger

log4j2
log4j
logback


## 日志系统和日志接口

日志系统
日志接口的具体实现。经典的有log4j，jdk自带的有java.util.Logging，还有log4j作者推出的被高度评价的logBack等等。

日志接口（门面）
如果只存在一种日志系统，日志接口完全没有必要存在（logBack无法独立使用）。但事与愿违，为了解决多个日志系统的兼容问题，日志接口应运而生。主流的日志接口有commons-logging和sl4j。



java很火，日志记录又是必需，因此就有了大量的日志记录框架，在日常使用时因为日志框架冲突引发各种问题，本文对之进行梳理，适合入门。由于作者水平限制，本文可能不够清晰甚至有错，烦请指出。在文章末尾的参考部分，有几位大牛总结的文章，推荐给大家。

下文将着重介绍上述日志框架的区别，以及避免不同框架组合引入时的冲突。


Java常用日志框架历史
1996年早期，欧洲安全电子市场项目组决定编写它自己的程序跟踪API(Tracing API)。经过不断的完善，这个API终于成为一个十分受欢迎的Java日志软件包，即Log4j。后来Log4j成为Apache基金会项目中的一员。

期间Log4j近乎成了Java社区的日志标准。据说Apache基金会还曾经建议sun引入Log4j到java的标准库中，但Sun拒绝了。

2002年Java1.4发布，Sun推出了自己的日志库JUL(Java Util Logging),其实现基本模仿了Log4j的实现。在JUL出来以前，log4j就已经成为一项成熟的技术，使得log4j在选择上占据了一定的优势。

接着，Apache推出了Jakarta Commons Logging，JCL只是定义了一套日志接口(其内部也提供一个Simple Log的简单实现)，支持运行时动态加载日志组件的实现，也就是说，在你应用代码里，只需调用Commons Logging的接口，底层实现可以是log4j，也可以是Java Util Logging。

后来(2006年)，Ceki Gülcü不适应Apache的工作方式，离开了Apache。然后先后创建了slf4j(日志门面接口，类似于Commons Logging)和Logback(Slf4j的实现)两个项目，并回瑞典创建了QOS公司，QOS官网上是这样描述Logback的：The Generic，Reliable Fast&Flexible Logging Framework(一个通用，可靠，快速且灵活的日志框架)。

现今，Java日志领域被划分为两大阵营：Commons Logging阵营和SLF4J阵营。
Commons Logging在Apache大树的笼罩下，有很大的用户基数。但有证据表明，形式正在发生变化。2013年底有人分析了GitHub上30000个项目，统计出了最流行的100个Libraries，可以看出slf4j的发展趋势更好：


Apache眼看有被Logback反超的势头，于2012-07重写了log4j 1.x，成立了新的项目Log4j 2。Log4j 2具有logback的所有特性。

框架对比
Commons Logging和Slf4j是日志门面，log4j和Logback则是具体的日志实现方案。
比较常用的组合使用方式是Slf4j与Logback组合使用，Commons Logging与Log4j组合使用。
至此，你应该了解了它们之间的区别，下面就说说使用时jar包的引入，避免冲突

在项目中选择日志框架
如果是在一个新的项目中建议使用Slf4j + Logback组合，这样有如下的几个优点。

Slf4j实现机制决定Slf4j限制较少，使用范围更广。由于Slf4j在编译期间，静态绑定本地的LOG库使得通用性要比Commons logging要好。
Logback拥有更好的性能

Logback声称：某些关键操作，比如判定是否记录一条日志语句的操作，其性能得到了显著的提高。这个操作在Logback中需要3纳秒，而在Log4J中则需要30纳秒。LogBack创建记录器（logger）的速度也更快：13毫秒，而在Log4J中需要23毫秒。更重要的是，它获取已存在的记录器只需94纳秒，而Log4J需要2234纳秒，时间减少到了1/23。跟JUL相比的性能提高也是显著的。
Commons Logging开销更高

在使Commons Logging时为了减少构建日志信息的开销，通常的做法是：

if(log.isDebugEnabled()){
log.debug("User name： " +
user.getName() + " buy goods id ：" + good.getId());
}
在Slf4j阵营，你只需这么做：

 log.debug("User name：{} ,buy goods id ：{}", user.getName(),good.getId());
也就是说，slf4j把构建日志的开销放在了它确认需要显示这条日志之后，减少内存和cup的开销，使用占位符号，代码也更为简洁

Logback文档免费。Logback的所有文档是全面免费提供的，不象Log4J那样只提供部分免费文档而需要用户去购买付费文档。






## Java自身日志框架

## Spring Boot集成logback

## Spring Boot集成log4j2

日志级别


ELK日志框架搭建




//使用  补零等操作
String.format();








通用概念

## 日志级别


## 日志输出格式

## 日志输出目的

topicName 和classname

## 相关链接

[spring boot 集成 log4j2分享](https://blog.csdn.net/boom_man/article/details/84031063)