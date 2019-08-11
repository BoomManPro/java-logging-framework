## 一、简介
java官方日志jul，位于java.util.logging包下。

## 二、jul模块
jul模块主要包含三个：Level、Formatter和Handler。

### 1、Level
日志级别，由高到低有：OFF/SEVERE/WARNIN/INFO/CONFIG/FINE/FINERG/FINEST/ALL，
每个级别有自己的数值，在java.util.logging.Level类中源码如下：
```java
public static final Level OFF = new Level("OFF",Integer.MAX_VALUE, defaultBundle);
public static final Level SEVERE = new Level("SEVERE",1000, defaultBundle);
public static final Level WARNING = new Level("WARNING", 900, defaultBundle);
public static final Level INFO = new Level("INFO", 800, defaultBundle);
public static final Level CONFIG = new Level("CONFIG", 700, defaultBundle);
public static final Level FINE = new Level("FINE", 500, defaultBundle);
public static final Level FINER = new Level("FINER", 400, defaultBundle);
public static final Level FINEST = new Level("FINEST", 300, defaultBundle);
public static final Level ALL = new Level("ALL", Integer.MIN_VALUE, defaultBundle);
```

可自定义日志级别，继承java.util.logging.Level类即可。

### 2、Formatter
定义日志输出的格式，目前有SimpleFormatter和XMLFormatter两种格式，分别对应简单格式和xml格式。
可自定输出格式，继承抽象类java.util.logging.Formatter即可。

### 3、Handler

日志输出的目的，目前有
FileHandler：输出到文件，默认Level为INFO，Formatter为XMLFormatter；；
ConsoleHandler：输出到控制台，默认Level为INFO，流为System.err，Formatter为SimpleFormatter；
SocketHandler：输出到socket；
MemoryHandler：输出到内存；
可自定义日志级别，继承java.util.logging.Formatter类即可。





## 参照链接
[java日志(二)--java官方日志jul的使用](https://blog.csdn.net/chinabestchina/article/details/84845691)