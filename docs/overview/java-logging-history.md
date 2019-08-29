## Java 日志发展史


## 说起Java日志你会想起什么

`SUN` `Apache` `Spring`

`Sl4j` `JCL` `JakartaCommons Logging` `Java Commons Logging` `Apache Commons Logging`

`Java Util Logging` `JUL``Java Logging` `Log4j` `Log4j2` `Logback` 

`Ceki Gülcü`


## 日志发展史

一、Sun在1996 年1月23日发布了JDK1.0

二、J2SE 1.4.0 Merlin 灰背隼 2002-02-13  Jul->java util logging 出现 即java自身的日志框架有了
<br/>

**那么问题来了 1996-2002 Java开发者使用什么记录日志呢?**
<br/><br/>
**方式一、 Java自身的输出流**

在JDK 1.3及以前，Java打日志依赖

`System.out.println() ` `System.err.println()` `e.printStackTrace()`

Debug日志被写到STDOUT流，错误日志被写到STDERR流。这样打日志有一个非常大的缺陷，即无法定制化，且日志粒度不够细。

**方式二、Log4j**

`Ceki Gülcü`于2001年发布了Log4j，后来成为Apache 基金会的顶级项目。
 
 
Log4j 在设计上非常优秀，对后续的 Java Log 框架有长久而深远的影响，它定义的Logger、Appender、Level等概念如今已经被广泛使用。Log4j 的短板在于性能，在Logback 和 Log4j2 出来之后，Log4j的使用也减少了。


**JUL和Log4j**

```
你可以选择log4j，java.util.logging。
不过我仍然强烈建议使用log4j，因为log4j性能很高，log输出信息时间几乎等于System.out，而处理一条log平均只需 要5us。
```

**Sun和Apache**

当初Apache极力游说Sun把log4j加入JDK1.4， 然而JDK1.4项目小组已经接近发布JDK1.4产品的时间了，因此拒绝了Apache的要求，使用自己的java.util.logging，这个包 的功能比log4j差的很远，性能也一般。


### Apache Log4j


Log4j 是基于Java开发的日志框架，其作者Ceki Gülcü将Log4j捐献给了Apache软件基金会，使之成为了Apache日志服务的一个子项目。 
Log4j虽然是Java日志服务，因其出色的表现，被孵化出了支持C, C++, C#, Perl, Python, Ruby等语言的子框架。 
Log4j早在1996年就被创立，之后经过改进与增强，正式对外发布。 
log4j  End of Life in August 2015 ，Apache软件基金业宣布，Log4j不在维护，建议所有相关项目升级到Log4j2.



## Apache Commons Logging

Apache Commons Logging,又名JakartaCommons Logging (JCL) 也是spring自身在使用的日志框架

当2002年Java开发出Java-Util-Logging时 `先有Log4j后有JUL` 

### 出现背景

**当一个系统同时使用这两个日志框架时，那会不会出现问题?**


想象下你的项目应用使用 Log4j，然后使用了一个第三方库，而第三方库使用了 JUL.
那么，你的应用就得同时使用 Log4j 和JUL两个日志工具了，然后又有需要使用另外一个第三方库，但是这个第三方库使用了 Log4j 和 JUL 之外的 simplelog。
这个时候你的应用里各种 log 工具满天飞，这势必会使你的程序员感到崩溃。因为这些日志工具互相没有关联，替换和统一日志工具也就变成了比较棘手的一件事情。

**如果你遇到了这种问题，你该如何解决呢？**


解决这个问题，我们会用到一个设计模式——`适配器模式`，即把这个问题进行抽象，抽象出一个接口层，对每个日志实现都进行适配，这样这些提供给别人的库都直接使用抽象的接口层即可。

为了搞定这个日常开发中比较棘手的问题，Apache 开源社区提供了一个日志框架作为日志的抽象，叫 commons-logging，也被称为 JCL(java common logging)，JCL 对各种日志接口进行抽象，抽象出一个接口层，对每个日志实现都进行适配，这样这些提供给别人的库都直接使用抽象层即可，确实出色地完成了兼容主流的日志实现（Log4j、JUL、simplelog 等），较好的解决了上述问题，基本一统江湖，就连顶顶大名的 spring 也是依赖了 JCL。

## SLF4J 和 Logback

### 出现背景

元老级日志 Log4j 的作者 (Ceki Gülcü)，他觉得 JCL 不够优秀，所以他再度出山，搞出了一套更优雅的日志框架 SLF4J（这个也是抽象层），
即简单日志门面（Simple Logging Facade for Java），并为 SLF4J 实现了一个亲儿子——logback，确实更加优雅了。

### Logback


logback当前分成三个模块：logback-core,logback- classic和logback-access。logback-core是其它两个模块的基础模块。

logback-classic是log4j的一个改良版本。此外logback-classic完整实现SLF4J API使你可以很方便地更换成其它日记系统如log4j或JDK14 Logging

与Log4j相比，Logback重新了内核，使它的性能提升了很多，大约是Log4j的10倍，同时占用更小的内存，并且完整的实现了SLF4J API是你可以很方便的切换日志框架。



## Log4j2

### 出现背景

Ceki Gülcü 觉得还是得照顾下自己的 “大儿子”——Log4j，又把 Log4j 进行了改造，就是所谓的 Log4j2，同时支持 JCL 以及 SLF4J。

### 优点
Log4j2有着和Logback相同的功能，但又有自己单用的功能，比如：插件式结构、配置文件优化、异步日志等。

Log4j2是Log4j的升级，它比其前身Log4j 1.x提供了重大改进，并提供了Logback中可用的许多改进，同时修复了Logback架构中的一些固有问题。

从GitHub的更新日志来看，Logback已经有半年没有更新了，而作为知名组织的Apache下的Log4j2的更新却是非常活跃的，Log4j 1.x 于2015年8月停止维护更新了。

## Java日志体系


下面是一张目前 Java 日志体系的示意图：

![日志体系](https://raw.githubusercontent.com/BoomManPro/java-logging-framework/master/docs/stastic/images/java-logging-example.jpg)



`Ceki Gülcü` ---->  Log4j -> Apache

`Ceki Gülcü` ---->  Sl4j

`Ceki Gülcü` ---->  Logback 原生支持Sl4j

`Ceki Gülcü` ---->  Log4j2

## 相关资料

工具 | 官方网站
-|-|
Log4j | [http://logging.apache.org/log4j/1.2](http://logging.apache.org/log4j/1.2) | 
JCL | [http://commons.apache.org/proper/commons-logging/](http://commons.apache.org/proper/commons-logging/) | 
SLF4J | 	[http://www.slf4j.org](http://www.slf4j.org) | 
logback | [http://logback.qos.ch](http://logback.qos.ch) | 
Log4j2 | [https://logging.apache.org/log4j/2.x/](https://logging.apache.org/log4j/2.x/) | 

	