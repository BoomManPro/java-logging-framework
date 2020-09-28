# 单体项目日志实战

## 背景

因为我司项目大多为单体项目,经过多个项目实施交付部署,总结出一些交付日志规范.

以下基于logback. why use it ? spring boot 自带,没有学习成本,大家都会用.

项目jar包为 mini-game-server.jar 为例

## 痛点

1. 日志级别动态修改(当现场出现问题时,需要修改日志级别) logback外部化配置
2. 日志压缩保留 存档等
3. 开发环境和线上生产日志管理问题
4. linux nohup.out日志过大问题

### 1. 线上日志级别动态修改

logback-spring.xml

使用 `scanPeriod` 配置用来定时扫描外部化配置文件 `config/logging-config.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>

<!-- For assistance related to logback-translator or configuration files in general,                    -->
<!--    please contact the logback user mailing list at http://www.qos.ch/mailman/listinfo/logback-user -->
<!-- For professional support please see http://www.qos.ch/shop/products/professionalSupport            -->
<configuration scan="true" scanPeriod="1 seconds">
    <!-- 引入平台公共的日志配置 配置了info和error-->
    <include resource="logging/logback-initial.xml"/>
    <include optional="true" file="config/logging-config.xml"/>
</configuration>
```

### 2. 日志压缩保留 存档等


服务器: `config/logback-config.xml`
```xml
<?xml version="1.0" encoding="UTF-8"?>

<!-- For assistance related to logback-translator or configuration files in general,                    -->
<!--    please contact the logback user mailing list at http://www.qos.ch/mailman/listinfo/logback-user -->
<!-- For professional support please see http://www.qos.ch/shop/products/professionalSupport            -->
<!-- Base logback configuration provided for compatibility with ecsplatform like Spring boot.           -->
<included>

    <!--  日志配置  -->
    <appender name="FILE_APPENDER" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 被写入的文件名，可以是相对目录，也可以是绝对目录，如果上级目录不存在会自动创建 -->
        <file>${file_log_base_home}/app.log</file>
        <!-- 当发生滚动时，决定RollingFileAppender的行为，涉及文件移动和重命名。属性class定义具体的滚动策略类 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 必要节点，包含文件名及"%d"转换符，"%d"可以包含一个java.text.SimpleDateFormat指定的时间格式，默认格式是 yyyy-MM-dd -->
            <fileNamePattern>${file_log_app_history_home}/app_%d{yyyy-MM-dd}.log.%i.gz</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>200MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <!--       防止项目启动不删除日志     -->
            <cleanHistoryOnStart>true</cleanHistoryOnStart>
            <!-- 可选节点，控制保留的归档文件的最大数量，超出数量就删除旧文件。假设设置每个月滚动，如果是6，则只保存最近6个月的文件，删除之前的旧文件 -->
            <maxHistory>10</maxHistory>
        </rollingPolicy>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>${file_log_pattern}</pattern>
        </encoder>
    </appender>

    <springProfile name="default">
        <!-- 打印控制台的信息 -->
        <root level="INFO">
            <appender-ref ref="FILE_APPENDER"/>
        </root>
    </springProfile>


</included>
```

### 3. 开发环境和线上生产日志管理问题

采用 spring profile管理

```xml
<?xml version="1.0" encoding="UTF-8"?>

<!-- For assistance related to logback-translator or configuration files in general,                    -->
<!--    please contact the logback user mailing list at http://www.qos.ch/mailman/listinfo/logback-user -->
<!-- For professional support please see http://www.qos.ch/shop/products/professionalSupport            -->
<!-- Base logback configuration provided for compatibility with ecsplatform like Spring boot.           -->
<included>

    <!--初始化-预制的日志appender-->
    <include resource="logging/logback-base.xml"/>


    <springProfile name="dev">
        <!-- 打印控制台的信息 -->
        <root level="INFO">
            <appender-ref ref="CONSOLE"/>
        </root>
        <logger name="org.springframework.security.web" level="DEBUG">
            <appender-ref ref="CONSOLE"/>
        </logger>
    </springProfile>

</included>
```

logback-base.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>

<!-- For assistance related to logback-translator or configuration files in general,                    -->
<!--    please contact the logback user mailing list at http://www.qos.ch/mailman/listinfo/logback-user -->
<!-- For professional support please see http://www.qos.ch/shop/products/professionalSupport            -->
<!-- Base logback configuration provided for compatibility with ecsplatform like Spring boot.           -->
<included>

    <!--应用名称-->
    <property name="web-app_name" value="mini-game"/>
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

#### 4. linux nohup.out日志过大问题

不要产生 nohup.out  `>/dev/null`

日志统一出口 就是app.log

如果项目spring boot启动banner不打印。
修改 application.properties `spring.main.banner-mode=log`

```bash

#!/bin/sh


##项目服务名称

SERVICE_NAME=mini-game-server

# java虚拟机启动参数

JAVA_OPTS="-ms512m -mx512m -Xmn256m -Djava.awt.headless=true -XX:MaxPermSize=128m"

## 脚本所在目录

BASEPATH=$(cd `dirname $0`; pwd)

## 以脚本所在目录为执行目录
cd $BASEPATH

## 相关Config

JAR_NAME=$SERVICE_NAME\.jar
JAR_HOME=$BASEPATH/jar/

##pid监控文件 内容是程序的pid
PID=$BASEPATH/$SERVICE_NAME\.pid

## 日志文件名称
LOG_NAME=app.log

##服务目录
SERVICE_LOGS=logs



## 使用说明,用来提示输入参数
usage(){
   echo "Usage: sh 执行脚本.sh [start|stop|restart|status]"
   # 退出
   exit 1

   ## help
   ## $? 是上个函数返回值
   ## Shell中判断语句if中-z至-d的意思 https://www.cnblogs.com/coffy/p/5748292.html
}


## 检查程序是否在运行
is_exist(){
  ## grep -v 排除 grep字段
  pid=`ps -ef |grep $JAR_NAME |grep -v grep|awk '{print $2}' `
  if [[ -z "$pid" ]]; then
  rm -rf $PID
  fi
  return $pid
}

#启动方法

init(){
  ## 判断logs目录是否存在,不存在则创建
  if [ ! -d "$BASEPATH/$SERVICE_LOGS" ];then
  mkdir -p $BASEPATH/$SERVICE_LOGS
  fi

  if [ ! -f "$BASEPATH/$SERVICE_LOGS/LOG_NAME" ];then
  touch $BASEPATH/$SERVICE_LOGS/$LOG_NAME
  fi
}


start(){
  is_exist
  init
  if [ ! -f "$PID" ];then
    nohup java $JAVA_OPTS -jar -Dloader.path=lib $JAR_HOME$JAR_NAME >/dev/null 2>&1 &
    echo $! > $PID
    ## 根据传参 判断 是自动构建工具还是直接运行  构建工具 需要睡眠5s,然后 打印出最后100行
    if [[ $1 == "ci" ]]; then
    sleep 5s
     tail  -n 100 $SERVICE_LOGS/$LOG_NAME
    else
    tail -f $SERVICE_LOGS/$LOG_NAME
    fi
    echo ">>> start $JAR_NAME successed PID=$! <<<"
  else
    echo ">>> $JAR_NAME  already start <<<"
    status
  fi
}

#停止方法 根据pid文件停止进程
stop(){

   is_exist
   if [[ -z "$pid" ]]; then
      echo ">>> jarName:[$JAR_NAME] pid:[$pidf]  process stopped success<<<"
      return 0
   fi

   if [ ! -f "$PID" ];then
      echo -e "\npid文件不存在"
      return 0
   else
      pidf=$(cat $PID)
      #echo "$pidf"
      echo ">>> api PID = $pidf begin kill $pidf <<<"
      kill -15 $pidf
      rm -rf $PID
      sleep 5
      kill -9 $pidf
      sleep 2
      echo ">>> jarName:[$JAR_NAME] pid:[$pidf]  process stopped success<<<"
      return 0
   fi
}

#输出运行状态
status(){
  is_exist
  if [[ -z "$pid" ]]; then
    echo ""
    echo "${JAR_NAME} is not running "
    echo ""
    rm -rf $PID
  else
    echo -e "\n${JAR_NAME} is running PID is $pid "
    detail=`ps -ef |grep $JAR_NAME |grep -v grep`
    echo -e "\n$detail\n"
  fi
}

#重启
restart(){
  stop
  start $1
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
  "start")
    start "$2"
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "restart")
    restart "$2"
    ;;
  *)
    usage
    ;;
esac
exit 0
```