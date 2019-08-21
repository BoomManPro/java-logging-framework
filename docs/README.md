## Java日志发展史

J2SE 1.4.0 Merlin 灰背隼 2002-02-13 Jul->java util logging 出现 即java自身的日志框架有了

Apache Commons Logging,又名JakartaCommons Logging (JCL) 也是spring自身在使用的日志框架

JCL诞生的初衷是因为Java自身的一些包用了JUL，而Log4j用户使用的有很多，那么JCL就是提供一套API来实现不同Logger之间的切换。


Apache Commons包中的一个，包含了日志功能，必须使用的jar包。这个包本身包含了一个Simple Logger，但是功能很弱。
在运行的时候它会先在CLASSPATH找log4j，如果有，就使用log4j，如果没有，就找JDK1.4带的 java.util.logging，如果也找不到就用Simple Logger。
commons-logging.jar的出现是一个历史的的遗留的遗憾，当初Apache极力游说Sun把log4j加入JDK1.4， 然而JDK1.4项目小组已经接近发布JDK1.4产品的时间了
，因此拒绝了Apache的要求，使用自己的java.util.logging，这个包 的功能比log4j差的很远，性能也一般。
后来Apache就开发出来了commons-logging.jar用来兼容两个logger。
因此用 commons-logging.jar写的log程序，底层的Logger是可以切换的，
你可以选择log4j，java.util.logging或 者它自带的Simple Logger。不过我仍然强烈建议使用log4j，
因为log4j性能很高，log输出信息时间几乎等于System.out，而处理一条log平均只需 要5us。
你可以在Hibernate的src目录下找到Hibernate已经为你准备好了的log4j的配置文件，你只需要到Apache 网站去下载log4j就可以了。
commons-logging.jar也是必须的jar包。 


Apache通用日志包提供一组通用的日志接口,用户可以自由选择实现日志接口的第三方软件
通用日志目前支持以下日志实现
Log4j日志器
JDK1.4Logging
SimpleLog日志器
NoOpLog日志器


Sun在1996 年1月23日发布了JDK1.0

log4j发展史


https://logging.apache.org/log4j/2.x/manual/index.html  1996年
  属于 Apache
  
log4j  End of Life in August 2015

Log4j 是基于Java开发的日志框架，其作者Ceki Gülcü将Log4j捐献给了Apache软件基金会，使之成为了Apache日志服务的一个子项目。 
Log4j虽然是Java日志服务，因其出色的表现，被孵化出了支持C, C++, C#, Perl, Python, Ruby等语言的子框架。 
Log4j早在1996年就被创立，之后经过改进与增强，正式对外发布。 
2015年9月，Apache软件基金业宣布，Log4j不在维护，建议所有相关项目升级到Log4j2.



Logback是Log4j的作者的另一个开源日志组件，与Log4j相比，Logback重新了内核，使它的性能提升了很多，大约是Log4j的10倍，同时占用更小的内存，并且完整的实现了SLF4J API是你可以很方便的切换日志框架。


SLF4J（Simple Logging Facade For Java）简单日志门面，和JCL功能类似，但JCL有一个致命的缺点就是算法复杂，出现问题难以排除，而SLF4J的诞生就是为了解决JCL的缺点。

值得一提的是SLF4J的作者就是Log4j的作者。


Log4j2有着和Logback相同的功能，但又有自己单用的功能，比如：插件式结构、配置文件优化、异步日志等。

Log4j2是Log4j的升级，它比其前身Log4j 1.x提供了重大改进，并提供了Logback中可用的许多改进，同时修复了Logback架构中的一些固有问题。

从GitHub的更新日志来看，Logback已经有半年没有更新了，而作为知名组织的Apache下的Log4j2的更新却是非常活跃的，Log4j 1.x 于2015年8月停止维护更新了。


jboss logging


Log4j
最先出现的是apache开源社区的log4j，这个日志确实是应用最广泛的日志工具，成为了java日志的事实上的标准。然而，当时Sun公司在jdk1.4中增加了JUL日志实现，企图对抗log4j，但是却造成了混乱，这个也是被人诟病的一点。当然也有其他日志工具的出现，这样必然造成开发者的混乱，因为这些日志系统互相没有关联，替换和统一也就变成了比较棘手的一件事。想象下你的应用使用log4j，然后使用了一个其他团队的库，他们使用了JUL，你的应用就得使用两个日志系统了，然后又有第二个库出现了，使用了simplelog。这个时候估计让你崩溃了，这是要闹哪样？这个状况交给你来想想办法，你该如何解决呢？进行抽象，抽象出一个接口层，对每个日志实现都适配或者转接，这样这些提供给别人的库都直接使用抽象层即可。不错，开源社区提供了commons-logging抽象，被称为JCL，也就是日志框架了，确实出色地完成了兼容主流的日志实现（log4j、JUL、simplelog），基本一统江湖，就连顶顶大名的spring也是依赖了JCL。看起来事物确实是美好，但是美好的日子不长，接下来另一个优秀的日志框架slf4j的加入导致了更加混乱的场面。比较巧的是slf4j的作者(Ceki Gülcü)就是log4j的作者，他觉得JCL不够优秀，所以他要自己搞一套更优雅的出来，于是slf4j日志体系诞生了，并为slf4j实现了一个亲子——logback，确实更加优雅，但是由于之前很多代码库已经使用JCL，虽然出现slf4j和JCL之间的桥接转换，但是集成的时候问题依然多多，对很多新手来说确实会很懊恼，因为比单独的log4j时代“复杂”多了，可以关注下这个，抱怨声确实很多。到此本来应该完了，但是Ceki Gülcü觉得还是得回头拯救下自己的“大阿哥”——log4j，于是log4j2诞生了，同样log4j2也参与到了slf4j日志体系中，想必将来会更加混乱。接下来详细解读日志系统的配合使用问题。

在JDK 1.3及以前，Java打日志依赖System.out.println(), System.err.println()或者e.printStackTrace()，Debug日志被写到STDOUT流，错误日志被写到STDERR流。这样打日志有一个非常大的缺陷，即无法定制化，且日志粒度不够细。
于是， Gülcü 于2001年发布了Log4j，后来成为Apache 基金会的顶级项目。Log4j 在设计上非常优秀，对后续的 Java Log 框架有长久而深远的影响，它定义的Logger、Appender、Level等概念如今已经被广泛使用。Log4j 的短板在于性能，在Logback 和 Log4j2 出来之后，Log4j的使用也减少了。

J.U.L
受Logj启发，Sun在Java1.4版本中引入了java.util.logging，但是j.u.l功能远不如log4j完善，开发者需要自己编写Appenders（Sun称之为Handlers），且只有两个Handlers可用（Console和File），j.u.l在Java1.5以后性能和可用性才有所提升。

https://www.cnblogs.com/caoweixiong/p/11285748.html
## 日志自动清理




# Java Logging Framework


## Overview

一个在生产环境里运行的程序如果没有日志是很让维护者提心吊胆的，有太多杂乱又无意义的日志也是令人伤神。程序出现问题时候，从日志里如果发现不了问题可能的原因是很令人受挫的。本文想讨论的是如何在Java程序里写好日志。

一般来说日志分为两种：业务日志和异常日志，使用日志我们希望能达到以下目标：

对程序运行情况的记录和监控；
在必要时可详细了解程序内部的运行状态；
对系统性能的影响尽量小；

## Java日志框架

Java的日志框架太多了。。。

Log4j 或 Log4j 2 - Apache的开源项目，通过使用Log4j，我们可以控制日志信息输送的目的地是控制台、文件、GUI组件、甚至是套接口服务器、NT的事件记录器、UNIX Syslog守护进程等；用户也可以控制每一条日志的输出格式；通过定义每一条日志信息的级别，用户能够更加细致地控制日志的生成过程。这些可以通过一个配置文件（XML或Properties文件）来灵活地进行配置，而不需要修改程序代码。Log4j 2则是前任的一个升级，参考了Logback的许多特性；
Logback - Logback是由log4j创始人设计的又一个开源日记组件。logback当前分成三个模块：logback-core,logback- classic和logback-access。logback-core是其它两个模块的基础模块。logback-classic是log4j的一个改良版本。此外logback-classic完整实现SLF4J API使你可以很方便地更换成其它日记系统如log4j或JDK14 Logging；
java.util.logging - JDK内置的日志接口和实现，功能比较简；
Slf4j - SLF4J是为各种Logging API提供一个简单统一的接口），从而使用户能够在部署的时候配置自己希望的Logging API实现；
Apache Commons Logging - Apache Commons Logging （JCL）希望解决的问题和Slf4j类似。
选项太多了的后果就是选择困难症，我的看法是没有最好的，只有最合适的。在比较关注性能的地方，选择Logback或自己实现高性能Logging API可能更合适；在已经使用了Log4j的项目中，如果没有发现问题，继续使用可能是更合适的方式；我一般会在项目里选择使用Slf4j, 如果不想有依赖则使用java.util.logging或框架容器已经提供的日志接口。


## Java日志最佳实践


						个人认为，如果在公司的野蛮生长阶段，一些基础类库不做约束，很可能“埋坑”，形成技术债务，最终为此付出代价。本文讲解一个最简的日志打印规范。
事实上，日志打印规范互联网上已有很多，但大多比较冗长（记不住），也不太契合我们团队（关注点不契合）。
所以，我又造了个轮子，写了个简单易懂、容易记的“最简日志打印规范”，后续随着团队实力的增长，和项目的演进，会逐步增加新的条例。
1. 日志打印组件
日志组件有很多，日志门面的选择有：Slf4j、Apache Commons Logging等。
日志的实现更多，有：log4j、logback、log4j2、Java Util Logging(jul)、Jboss Logging等。
目前，我们使用Slf4j作为日志门面，logback作为日志实现。
2. 日志打印级别


日志级别 日志打印场景


日志的级别有很多，我们一般只用四个。日志级别由低到高DEBUG – INFO – WARN – ERROR。

DEBUG（调试）：开发调试日志。一般来说，在系统实际运行过程中，不会输出该级别的日志。因此，开发人员可以打印任何自己觉得有利于了解系统运行状态的东东。不过很多场景下，过多的DEBUG日志，并不是好事，建议是按照业务逻辑的走向打印。打个比方，打印日志就像读书时划重点，如果导出都是重点，也就失去了重点。
INFO（通知）：INFO日志级别主要用于记录系统运行状态等关联信息。该日志级别，常用于反馈系统当前状态给最终用户。所以，在这里输出的信息，应该对最终用户具有实际意义，也就是最终用户要能够看得明白是什么意思才行。
WARN（警告）：WARN日志常用来表示系统模块发生问题，但并不影响系统运行。 此时，进行一些修复性的工作，还能把系统恢复到正常的状态。
ERROR（错误）：此信息输出后，主体系统核心模块正常工作，需要修复才能正常工作。 就是说可以进行一些修复性的工作，但无法确定系统会正常的工作下去，系统在以后的某个阶段，很可能会因为当前的这个问题，导致一个无法修复的错误（例如宕机），但也可能一直工作到停止也不出现严重问题。





DEBUG
调试日志。目前管理相对宽松，我们暂时没有严格要求。




INFO
业务日志。我们用来记录业务的主流程的走向。


WARN
警告日志。一般来说，发生对整个系统没什么影响的异常时，可以打印该级别的日志。

ERROR
错误日志。级别比较高，如果发生一些异常，并且任何时候都需要打印时使用。



定义日志变量
日志变量往往不变，最好定义成final static，变量名用大写。

日志分级
Java的日志框架一般会提供以下日志级别，缺省打开info级别，也就是debug，trace级别的日志在生产环境不会输出，在开发和测试环境可以通过不同的日志配置文件打开debug级别。

fatal - 严重的，造成服务中断的错误；
error - 其他错误运行期错误；
warn - 警告信息，如程序调用了一个即将作废的接口，接口的不当使用，运行状态不是期望的但仍可继续处理等；
info - 有意义的事件信息，如程序启动，关闭事件，收到请求事件等；
debug - 调试信息，可记录详细的业务处理到哪一步了，以及当前的变量状态；
trace - 更详细的跟踪信息；
在程序里要合理使用日志分级:






在程序里要合理使用日志分级:



 

基本的Logger编码规范


使用的API

public static final Logger LOGGER = LoggerFactory.getLogger(MyRealm.class);

我们使用的日志门面是slf4j，使用时应面向接口编程，LOGGER/LoggerFactory应该都是slf4j的API。
严禁直接使用日志实现包。原因大致有两点，第一是面向接口编程更优雅，这点不必说明；第二，举个例子，因为log4j已经几年不更新，老的项目可能使用了log4j，现在想要换用logback或者log4j2，如直接使用log4j的API，不利于选型更换与API的统一（当然，非要用也没关系，有个log4j-over-slf4j的适配器。但接口不统一，总感觉哪里不对劲）。


1.在一个对象中通常只使用一个Logger对象，Logger应该是static final的，只有在少数需要在构造函数中传递logger的情况下才使用private final。



 

2.输出Exceptions的全部Throwable信息，因为logger.error(msg)和logger.error(msg,e.getMessage())这样的日志输出方法会丢失掉最重要的StackTrace信息。



3.不允许记录日志后又抛出异常，因为这样会多次记录日志，只允许记录一次日志。



4.不允许出现System print(包括System.out.println和System.error.println)语句。



5.不允许出现printStackTrace。



6.日志性能的考虑，如果代码为核心代码，执行频率非常高，则输出日志建议增加判断，尤其是低级别的输出<debug、info、warn>。



debug日志太多后可能会影响性能，有一种改进方法是：


无需使用级别判断

不优雅示例：
if (LOGGER.isDebugEnabled()) {
LOGGER.debug(“当前用户是{}”, token);
}
以前，为了性能，我们常常在打印日志之前判断一下。
使用Slf4j后，我们可以写成如下形式就OK了。
LOGGER.debug(“当前用户是{}”, token);
当然，如果依然使用字符串拼接的方式，还是得判断一下级别的。
```java
if (logger.isDebugEnabled()) {

logger.debug("Processing trade with id: " + id + " symbol: " + symbol);

}
```


但更好的方法是Slf4j提供的最佳实践:


```java

log.debug("returing content:{}",content);
```
一方面可以减少参数构造的开销，另一方面也不用多写两行代码。


7.有意义的日志

通常情况下在程序日志里记录一些比较有意义的状态数据：程序启动，退出的时间点；程序运行消耗时间；耗时程序的执行进度；重要变量的状态变化。

初次之外，在公共的日志里规避打印程序的调试或者提示信息。



禁止字符串拼接

例如：
LOGGER.debug(“当前用户是:” + user + “,传入参数是:” + userId);

严禁使用字符串拼接的方式打印日志，可读性、可维护性都比较差。
建议的写法如下：
LOGGER.debug(“当前用户是:{},传入参数是:{},返回值是:{},用户信息:{}”, a,b new Object[]{token, userId, userInfo, authcInfo});

因为我们使用的是Slf4j，Slf4j有占位符填充的功能。多个占位符可放在Object数组中。






## 使用规范

1. 【强制】应用中不可直接使用日志系统（ Log4j、 Logback）中的 API，而应依赖使用日志框架

SLF4J 中的 API，使用门面模式的日志框架，有利于维护和各个类的日志处理方式统一。

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

private static final Logger logger = LoggerFactory.getLogger(Abc.class);
```


2. 【强制】日志文件推荐至少保存 15 天，因为有些异常具备以"周" 为频次发生的特点。

3. 【强制】应用中的扩展日志（如打点、临时监控、访问日志等）命名方式：

appName_logType_logName.log 

logType:日志类型，推荐分类有 stats/desc/monitor/visit等;

logName:日志描述。这种命名的好处：通过文件名就可知道日志文件属于什么应用，什么类型，什么目的，也有利于归类查找。

正例： mppserver 应用中单独监控时区转换异常，如：

mppserver_monitor_timeZoneConvert.log

说明： 推荐对日志进行分类，错误日志和业务日志尽量分开存放，便于开发人员查看，也便于通过日志对系统进行及时监控。

4. 【强制】对 trace/debug/info 级别的日志输出，必须使用条件输出形式或者使用占位符的方式。

说明： logger.debug("Processing trade with id: " + id + " symbol: " + symbol); 
如果日志级别是 warn，上述日志不会打印，但是会执行字符串拼接操作，如果 symbol 是对象，会执行 toString()方法，浪费了系统资源，执行了上述操作，最终日志却没有打印。

正例： （条件）
```java
if (logger.isDebugEnabled()) {

logger.debug("Processing trade with id: " + id + " symbol: " + symbol);

}
```
正例： （占位符）
```java

logger.debug("Processing trade with id: {} and symbol : {} ", id, symbol);
```

5. 【强制】避免重复打印日志，浪费磁盘空间，务必在 log4j.xml 中设置 additivity=false.

正例： 
```xml
<logger name="com.taobao.ecrm.member.config" additivity="false"> </logger>
```


6. 【强制】异常信息应该包括两类信息：案发现场信息和异常堆栈信息。如果不处理，那么往上抛。
正例：
```java
 logger.error(各类参数或者对象 toString + "_" + e.getMessage(), e);
```


7. 输出的 POJO 类必须重写 toString 方法，否则只输出此对象的 hashCode 值（地址值），没啥参考意义。

8. 【推荐】可以使用 warn 日志级别来记录用户输入参数错误的情况，避免用户投诉时，无所适从。注意日志输出的级别， error 级别只记录系统逻辑出错、异常、或者重要的错误信息。如非必要，请不要在此场景打出 error 级别，避免频繁报警。

9. 【推荐】谨慎地记录日志。生产环境禁止输出 debug 日志；有选择地输出 info 日志；如果使用 warn 来记录刚上线时的业务行为信息，一定要注意日志输出量的问题，避免把服务器磁盘撑爆，并记得及时删除这些观察日志。

说明： 大量地输出无效日志，不利于系统性能提升，也不利于快速定位错误点。纪录日志时请思考：这些日志真的有人看吗？看到这条日志你能做什么？能不能给问题排查带来好处？

10. 【参考】如果日志用英文描述不清楚，推荐使用中文注释。对于中文 UTF-8 的日志，在 secureCRT中， set encoding=utf-8；如果中文字符还乱码，请设置：全局>默认的会话设置>外观>字体>选择字符集 gb2312；如果还不行，执行命令： set termencoding=gbk，并且直接使用中文来进行检索。



打印日志注意事项

错误说明+参数（参数引导用户核查错误），只打错误说明往往找不到问题

if (agentInfoDTOResult.getData() == null) {
    log.warn("代理不存在,参数id="+gentQueryParam.getId());
    return new FailResult(MaidErrorMsgEnum.AgentNotExitError);
}

不要打印出NPE
gentQueryParam.getId()为null就会NPE



1 Log的用途
不管是使用何种编程语言，日志输出几乎无处不再。总结起来，日志大致有以下几种用途：

l 问题追踪：通过日志不仅仅包括我们程序的一些bug，也可以在安装配置时，通过日志可以发现问题。

l 状态监控：通过实时分析日志，可以监控系统的运行状态，做到早发现问题、早处理问题。

l 安全审计：审计主要体现在安全上，通过对日志进行分析，可以发现是否存在非授权的操作。



2.1 日志的级别划分
Java日志通常可以分为：error、warn、info、debug、trace五个级别。在J2SE中预定义的级别更多，分别为：SEVERE、WARNING、INFO、CONFIG、FINE、FINER、FINEST。两者的对应大致如下：

|

Log4j、slf4j

|

J2se

|

使用场景

|
|

error

|

SEVERE

|

问题已经影响到软件的正常运行，并且软件不能自行恢复到正常的运行状态，此时需要输出该级别的错误日志。

|
|

warn

|

WARNING

|

与业务处理相关的失败，此次失败不影响下次业务的执行，通常的结果为外部的输入不能获得期望的结果。

|
|

info

|

INFO

|

系统运行期间的系统运行状态变化，或关键业务处理记录等用户或管理员在系统运行期间关注的一些信息。

|
|

CONFIG

|

系统配置、系统运行环境信息，有助于安装实施人员检查配置是否正确。

|
|

debug

|

FINE

|

软件调试信息，开发人员使用该级别的日志发现程序运行中的一些问题，排除故障。

|
|

FINER

|

基本同上，但显示的信息更详尽。

|
|

trace

|

FINEST

|

基本同上，但显示的信息更详尽。

|2.2 日志对性能影响
 不管是多么优秀的日志工具，在日志输出时总会对性能产生或多或少的影响，为了将影响降低到最低，有以下几个准则需要遵守：
 
 l 如何创建Logger实例：创建Logger实例有是否static的区别，在log4j的早期版本，一般要求使用static，而在高版本以及后来的slf4j中，该问题已经得到优化，获取（创建）logger实例的成本已经很低。所以我们要求：对于可以预见的多数情况下单例运行的class，可以不添加static前缀；对于可能是多例居多，尤其是需要频繁创建的class，我们要求要添加static前缀。
 
 l 判断日志级别：
 
 n对于可以预见的会频繁产生的日志输出，比如for、while循环，定期执行的job等，建议先使用if对日志级别进行判断后再输出。
 
 n对于日志输出内容需要复杂的序列化，或输出的某些信息获取成本较高时，需要对日志级别进行判断。比如日志中需要输出用户名，而用户名需要在日志输出时从数据库获取，此时就需要先判断一下日志级别，看看是否有必要获取这些信息。
 
 l 优先使用参数，减少字符串拼接：使用参数的方式输出日志信息，有助于在性能和代码简洁之间取得平衡。当日志级别限制输出该日志时，参数内容将不会融合到最终输出中，减少了字符串的拼接，从而提升执行效率。
 
 2.3 什么时候输出日志
 日志并不是越多越详细就越好。在分析运行日志，查找问题时，我们经常遇到该出现的日志没有，无用的日志一大堆，或者有效的日志被大量无意义的日志信息淹没，查找起来非常困难。那么什么时候输出日志呢？以下列出了一些常见的需要输出日志的情况，而且日志的级别基本都是>=INFO，至于Debug级别日志的使用场景，本节没有专门列出，需要具体情况具体分析，但也是要追求“恰如其分”，不是越多越好。
 
 2.3.1 系统启动参数、环境变量
 系统启动的参数、配置、环境变量、System.Properties等信息对于软件的正常运行至关重要，这些信息的输出有助于安装配置人员通过日志快速定位问题，所以程序有必要在启动过程中把使用到的关键参数、变量在日志中输出出来。在输出时需要注意，不是一股脑的全部输出，而是将软件运行涉及到的配置信息输出出来。比如，如果软件对jvm的内存参数比较敏感，对最低配置有要求，那么就需要在日志中将-Xms -Xmx -XX:PermSize这几个参数的值输出出来。
 
 2.3.2 异常捕获处
 在捕获异常处输出日志，大家在基本都能做到，唯一需要注意的是怎么输出一个简单明了的日志信息。这在后面的问题问题中有进一步说明。
 
 2.3.3 函数获得期望之外的结果时
 一个函数，尤其是供外部系统或远程调用的函数，通常都会有一个期望的结果，但如果内部系统或输出参数发生错误时，函数将无法返回期望的正确结果，此时就需要记录日志，日志的基本通常是warn。需要特别说明的是，这里的期望之外的结果不是说没有返回就不需要记录日志了，也不是说返回false就需要记录日志。比如函数：isXXXXX()，无论返回true、false记录日志都不是必须的，但是如果系统内部无法判断应该返回true还是false时，就需要记录日志，并且日志的级别应该至少是warn。
 
 2.3.4 关键操作
 关键操作的日志一般是INFO级别，如果数量、频度很高，可以考虑使用DEBUG级别。以下是一些关键操作的举例，实际的关键操作肯定不止这么多。
 
 n 删除：删除一个文件、删除一组重要数据库记录……
 
 n 添加：和外系统交互时，收到了一个文件、收到了一个任务……
 
 n 处理：开始、结束一条任务……
 
 n ……
 
 2.4 日志输出的内容
 l ERROR：错误的简短描述，和该错误相关的关键参数，如果有异常，要有该异常的StackTrace。
 
 l WARN：告警的简短描述，和该错误相关的关键参数，如果有异常，要有该异常的StackTrace。
 
 l INFO：言简意赅地信息描述，如果有相关动态关键数据，要一并输出，比如相关ID、名称等。
 
 l DEBUG：简单描述，相关数据，如果有异常，要有该异常的StackTrace。
 
 在日志相关数据输出的时要特别注意对敏感信息的保护，比如修改密码时，不能将密码输出到日志中。
 
 2.5 什么时候使用J2SE自带的日志
 我们通常使用slf4j或log4j这两个工具记录日志，那么还需要使用J2SE的日志框架吗？当然需要，在我们编写一些通用的工具类时，为了减少对第三方的jar包的依赖，首先要考虑使用java.util.logging。
 
 考虑到slf4j等日志框架提供了日志bridge工具，为java.util.logging提供了Handler，所以普通应用的开发过程中也可以考虑使用J2SE自有日志，这样不但可以减少项目的编译依赖，同时在应用实施时可以更灵活的选择日志的输出工具包。
 
 3 典型问题分析
 3.1 该用日志的地方不用
 上图对异常的处理直接使用e.printStackTrace()显然是有问题的，正确的做法是：要么通过日志方式输出错误信息，要么直接抛出异常，要么创建新的自定义异常抛出。
 
 另：对于静态工具类函数中的异常处理，最简单的方式就是不捕获、不记录日志，直接向上抛出，如果认为异常类型太多，或者意义不明确，可以抛出自定义异常类的实例。
 
 3.2 啰嗦重复、没有重点
 首先上面不应该有error级别的日志。
 
 其次在日志中直接输出e.toString()，为定位问题提供的信息太少。
 
 另外需要明确一点：日志系统是一个多线程公用的系统，在两行日志输出之间有可能会被插入其他线程的日志记录，不会按照我们的意愿顺序输出，后面有更典型的例子。
 
 最后，上面的日志可以简化为：
 
 logger.debug(“从properties中...{}...{}...”,name, value, e);
 
 logger.warn(“从properties中获取{}发生错误：{}”,name, e.toString());
 
 或者直接一句：
 
 logger.warn(“从properties中...{}...{}...”,name, value, e);
 
 或者更完美的：
 
 if(logger.isDebugEnabled()){
 
 logger.warn(“从properties中...{}...”, name, e);
 
 }else{
 
 logger.warn(“从properties中获取{}发生错误：{}”, name, e.toString());
 
 }
 
 3.3 日志和异常处理的关系
 首先上面的日志信息不够充分，级别定义不够恰当。
 
 另外，既然将异常捕获并记录的日志，就不应该重新将一个一模一样的异常再次抛出去了。如果将异常再次抛出，那在上层肯定还需要对该异常进行处理，并记录日志，这样就重复了。如果没有特别原因，此处不应该捕获异常。
 
 3.4 System.out方式的日志
 上面的日志形式十分随意，只适合临时的代码调试，不允许提交到正式的代码库中。
 
 对于临时调试日志，建议在日志的输出信息中添加一些特殊的连续字符，也可以用自己的名称、代号，这样可以在调试完毕后，提交代码之前，方便地找到所有临时代码，一并删除。
 
 3.5 日志信息不明确
 上面的“添加任务出错。。。”既没有记录任务id，也没有任务名称，软件部署后发现错误后，根据该日志记录不能确认哪一条任务错误，给进一步的分析原因带来困难。
 
 另外第二个红圈中的问题有：要使用参数；一行日志就可以了。
 
 还有一些其他共性的错误，就不多说了。
 
 3.6 忘记日志输出是多线程公用的
 如果有另外一个线程正在输出日志，上面的记录就会被打断，最终显示输出和预想的就会不一致。正确的做法应是将这些信息放到一行，如果需要换行可以考虑使用“\r”，如果内容较多，考虑增加if (logger.isDebugEnabled())进行判断。而第二个例子中的输出有System.out的习惯，相关内容应该一行完成。
 
 3.7 多个参数的处理
 对于多参的日志输出，可以考虑：
 
 public void debug(String format, Object... arguments);
 
 但是在使用多参时，会创建一个对象数组，也会有一定的消耗，为此，在对性能敏感的场景，可以增加对日志级别的判断。
 
 在开发B/S系统时，对于LOG，需要关注：
 
 日志信息的集中采集、存储、信息检索：在WEB集群节点越来越多的情况下，让开发及系统维护人员能很方便的查看日志信息
 日志信息的输出策略：日志信息输出全而不乱，便于跟踪和分析问题
 关键业务的日志输出：基于度量数据采集、数据核查、系统安全等方面的考虑，关键业务系统对输出的日志信息有特殊的要求，需要做针对性的设计
 本文主要从这3个方面进行说明，重点说明日志输出的使用
 
 日志的采集和存储
 对于目前存储日志，主要存在2种方式：
 
 本地日志：直接存放在本地磁盘上
 远程日志：发往�日志平台，用作数据分析和日志处理并展现。如用户访问行为的记录，异常日志，性能统计日志等。
 日志工具的选择
 推荐使用SLF4J（Simple Logging Facade for Java）作为日志的api，SLF4J是一个用于日志系统的简单Facade，允许最终用户在部署其应用时使用其所希望的日志系统。与使用apache commons-logging和直接使用log4j相比，SLF4J提供了一个名为参数化日志的高级特性，可以显著提高在配置为关闭日志的情况下的日志语句性能
 
 //slf4j
 log.debug("Found {} records matching filter: '{}'", records, filter);
 
 //log4j
 log.debug("Found " + records + " records matching filter: '" + filter + "'");
 
 可以看出SLF4J的方式一方面更简略易读，另一方面少了字符串拼接的开销,并且在日志级别达不到时（这里例子即为设置级别为debug以上），不会调用对象的toString方法。
 
 日志输出级别（由高到低）
 
 ERROR：系统中发生了非常严重的问题，必须马上有人进行处理。没有系统可以忍受这个级别的问题的存在。比如：NPEs（空指针异常），数据库不可用，关键业务流程中断等等
 
 WARN：发生这个级别问题时，处理过程可以继续，但必须对这个问题给予额外关注。这个问题又可以细分成两种情况：一种是存在严重的问题但有应急措施（比如数据库不可用，使用Cache）；第二种是潜在问题及建议（ATTENTION），比如生产环境的应用运行在Development模式下、管理控制台没有密码保护等。系统可以允许这种错误的存在，但必须及时做跟踪检查
 
 INFO：重要的业务处理已经结束。在实际环境中，系统管理员或者高级用户要能理解INFO输出的信息并能很快的了解应用正在做什么。比如，一个和处理机票预订的系统，对每一张票要有且只有一条INFO信息描述 "[Who] booked ticket from [Where] to [Where]"。另外一种对INFO信息的定义是：记录显著改变应用状态的每一个action，比如：数据库更新、外部系统请求
 
 DEBUG：用于开发人员使用。将在TRACE章节中一起说明这个级别该输出什么信息
 
 TRACE：非常具体的信息，只能用于开发调试使用。部署到生产环境后，这个级别的信息只能保持很短的时间。这些信息只能临时存在，并将最终被关闭。要区分DEBUG和TRACE会比较困难，对一个在开发及测试完成后将被删除的LOG输出，可能会比较适合定义为TRACE级别
 
 推荐使用debug，info，warn，error级别即可，对于不同的级别可以设置不同的输出路径,如debug，info输出到一个文件，warn，error输出到一个带error后缀的文件
 
 日志API规范
 
 Log对象的声明和初始化，仅以下代码是符合规范的
 // （推荐）
 private static final Logger logger = LoggerFactory.getLogger(Xxx.class);
 
 private final Logger logger = LoggerFactory.getLogger(getClass());
 
 private  static  final Logger logger =  LoggerFactory.getLogger("loggerName");
 
 private static Logger logger = LoggerFactory.getLogger(Xxx.class);
 
 protected final Logger logger = LoggerFactory.getLogger(getClass());
 
 private Logger logger = LoggerFactory.getLogger(getClass());
 
 protected Logger logger = LoggerFactory.getLogger(getClass());
 
 不得使用System.out, System.err进行日志记录，请改使用logger.debug、logger.error
 
 debug/info级别的信息，信息本身需要计算或合并的，必须加 isXxxEnabled() 判断在前，这样可以大大提高高并发下的效率。如：
 
 if (logger.isDebugEnabled()) {
     logger.debug(test());
 }
 
 private String test(){
     int i = 0;
     while (i < 1000000) {
         i++;
     }
 
     return "";
 }
 
 如果不加 isXxxEnabled() 判断，test()在info级别下也会执行。
 
 注意error和warn级别的区别，导致业务不正常服务的，用error级别；错误是预期会发生的，并且已经有了其他的处理流程，使用warn级别
 
 正确的记录异常信息
 记录异常信息是“记录所有信息”中的一个重要组成，但很多开发人员只是把logging当做处理异常的一种方式。他们通常返回缺省值，然后当做什么都没发生。 其他时候，他们先log异常信息，然后再抛出包装过的异常。如：
 
 log.error("IO exception", e);
 
 throw new MyCustomException(e);
 
 这种方法总是会打印两次相同的 stack trace信息，因为有些地方会捕捉MyCustomException异常，然后输出导致问题的日志信息。
 但有时基于某些原因我们真的想log异常信息怎么办？很过见多的log语句有一半以上都是错的，
 如：
 
 try {    
     Integer x = null;
     ++x;
 } catch (Exception e) {
 
     //A
     log.error(e);        
 
     //B
     log.error(e, e);        
 
     //C
     log.error(""+ e);        
 
     //D
     log.error(e.toString());        
 
     //E
     log.error(e.getMessage());        
 
     //F
     log.error(null, e);        
 
     //G
     log.error("", e);        
 
     //H
     log.error("{}", e);        
 
     //I
     log.error("{}", e.getMessage());      
 
      //J
      log.error("Error reading configuration file: " + e);        
 
     //K
     log.error("Error reading configuration file: "+ e.getMessage());        
 
     //L
     log.error("Error reading configuration file", e);        
 
 }
 
 上面只有G、L是对的，L的处理方式更好一些
 
 注意：捕获异常后不处理也不输出log是一种非常不负责任的行为，这会造成问题很难被定位，极大地提高debug成本！
 
 重要方法入口，业务流程前后及处理的结果等，推荐记录log，并使用debug级别，如：
 public String printDocument(Document doc, Mode mode) {
 
     log.debug("Entering printDocument(doc={}, mode={})", doc, mode);
 
     //Lengthy printing operation
     String id = "Id";
 
     log.debug("Leaving printDocument(): {}", id);
 
     return id;
 
 }
 
 因为对于非开发人员掌控的环境（无法做DEBUG），记录方法调用、入参、返回值的方式对于排查问题会有很大帮助。
 
 Log的内容一定要确保不会因为Log语句的问题而抛出异常造成中断，如：
 log.debug("Processing request with id: {}", request.getId());
 
 你能确保request对象不是NULL吗？如果request为null，就会抛出NullPointerException。
 
 避免拖慢应用系统
 输出太多日志信息：通常每小时输出到disk的数据量达到几百MiB就已经是上限了不正确使用toString() 或字符串拼接方法。
 try {
     log.trace("Id=" + request.getUser().getId() + " accesses" + manager.getPage().getUrl().toString())
 } catch(NullPointerException e) {
 }
 
 日志信息中尽量包含数据和描述：easy to read, easy to parse
 一些反模式的例子
 log.debug("Message processed");
 
 log.debug(message.getJMSMessageID());
 
 log.debug("Message with id '{}' processed", message.getJMSMessageID());
 
 if (message instanceof TextMessage)
 
 //...
 else {
     log.warn("Unknown message type");
 }
 
 为什么不包含 message type, message id, etc，包含个message content很难吗？另一个anti-pattern是magic-log。
 有些开发人员为了自己查找信息方便，输出类似“&&&!#”的Log，而不是“Message with XYZ id received”。
 最后，Log 不要涉及密码及个人信息（身份证、银行卡号etc）
 
 关键业务系统日志的要求
 用户浏览日志
 使用WEB服务器或应用服务器实现日志输出，关键信息包括：访问时间、用户IP、访问的URL、用户浏览器信息、HTTP状态码、请求处理时间
 用户登录日志
 用于记录用户的Login、Logout、CheckLogin请求情况，关键信息如下：
 Login：请求时间、用户IP、用户名、渠道信息、用户浏览器信息、登录处理结果、请求花费时间、tokenId、sessionid
 Logout：请求时间、用户IP、用户名、渠道信息、用户浏览器信息、登出结果、请求花费时间、tokenid、sessionid
 CheckLogin：请求时间、用户IP、用户名、渠道信息、用户浏览器信息、检查结果、检查花费时间、tokenid、sessionId
 服务接口调用日志
 所有外部接口的调用需要记录接口访问信息，关键信息如下：
 请求时间、接口URL、接口方法、调用结果、执行时间
 配置规范
 
 统一使用log4j.xml、log4j2.xml、logback配置。
 
 所有的jar包中不推荐包含log4j.xml、log4j.properties、logback.xml文件，避免干扰实际的业务系统。
 
 注意Logger间的继承关系，如：
 
 log4j的继承是通过命名来实现的。
 子logger会默认继承父logger的appender，将它们加入到自己的Appender中；除非加上了additivity="false"，则不再继承父logger的appender。
 子logger只在自己未定义输出级别的情况下，才会继承父logger的输出级别。
 Log文件位置和命名，目前Log文件的位置统一放在相同目录下面，Log名字通常以业务名开头，如xxx.log.2015-11-19等。
 
 日志格式：必选打印数据项: 发生时间、日志级别、日志内容,可选文件和行号。
 
 远程日志的输出需要注意host和port，区分cagegory。























