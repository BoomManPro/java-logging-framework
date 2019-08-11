package cn.boommanpro;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.IOException;
import java.util.logging.*;

/**
 * @author <a href="mailto:boommanpro@gmail.com">BoomManPro</a>
 * @date 2019/8/10 15:42
 * @created by BoomManPro
 */
@Slf4j
public class Main {

    public static void main(String[] args) throws IOException {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        //简单格式的Formatter
        SimpleFormatter sf = new SimpleFormatter();
        //xml格式的formatter
        XMLFormatter xf = new XMLFormatter();

        //输出到文件的hander,指定日志输出级别为ALL
        FileHandler fh = new FileHandler("jul_study.log");
        fh.setLevel(Level.ALL);
        fh.setFormatter(sf);
//        fh.setFormatter(xf);

        //输出到控制台的handler,指定日志输出级别为INFO
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.INFO);

        //获取logger实例，相同名的只能生成一个实例
        Logger logger = Logger.getLogger("javaLog");
        logger.addHandler(fh);   //添加输出handler
        logger.addHandler(ch);   //添加输出handler
        logger.setLevel(Level.ALL);  //指定logger日志级别

        //日志输出简写形式，有不同的级别，可带参数，其它类似
        logger.log(Level.INFO, "this is a info, {0}", "p1");
        logger.log(Level.WARNING, "this is a warn, {0} {1}", new Object[]{"p1", "p2"});
        logger.log(Level.SEVERE, "this is a severe");
        logger.log(Level.FINE, "this is a fine");
        logger.log(Level.FINEST, "this is a finest");
        logger.log(Level.CONFIG, "this is a config");
        logger.log(Level.INFO, "this is a info", new Exception("my excep")); //带异常输出

        //日志输出简写形式，有不同的级别
        logger.severe("severe log");
        logger.warning("warning log");
        logger.info("info log");
        logger.config("config log");
        logger.fine("fine log");
        logger.finer("finer log");
        logger.finest("finest log");

        log.error("Slf4j日志打印{}",Main.class);
    }
}
