package cn.boommanpro.springbootlogback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author <a href="mailto:boommanpro@gmail.com">BoomManPro</a>
 * @date 2019/8/11 18:33
 * @created by BoomManPro
 */
@SpringBootApplication
public class SpringBootLogbackApplication {

    private static final Logger log = LoggerFactory.getLogger(SpringBootLogbackApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLogbackApplication.class, args);
        log.info("{} Spring Boot项目启动成功 ",SpringBootLogbackApplication.class);
    }

}
