package cn.boommanpro.springbootlogback;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;

import org.junit.Test;

/**
 * @author <a href="mailto:wangqimeng@mininglamp.com">wangqimeng</a>
 * @date 2019/8/16 15:42
 * @created by wangqimeng
 */
public class ClassLoaderPrint {

    @Test
    public void test() {
        String[] appFiles = System.getProperty("java.class.path").split(";");

        Arrays.asList(appFiles).forEach(System.out::println);
    }

    @Test
    public void slf4jTest() throws IOException {
        String STATIC_LOGGER_BINDER_PATH = "org/slf4j/impl/StaticLoggerBinder.class";
        Enumeration<URL> paths= ClassLoader.getSystemResources(STATIC_LOGGER_BINDER_PATH);
        while (paths.hasMoreElements()) {
            URL path = paths.nextElement();
            System.out.println(path);
        }
    }

}
