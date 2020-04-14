package cn.boommanpro.springbootlogback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author <a href="mailto:boommanpro@gmail.com">BoomManPro</a>
 * @date 2019/8/11 18:33
 * @created by BoomManPro
 */
@Slf4j
@SpringBootApplication
public class SpringBootLogbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLogbackApplication.class, args);
        if (log.isDebugEnabled()) {
            //做大量运算
            log.debug("{} Spring Boot项目启动成功 ", SpringBootLogbackApplication.class);
        }
    }

}
