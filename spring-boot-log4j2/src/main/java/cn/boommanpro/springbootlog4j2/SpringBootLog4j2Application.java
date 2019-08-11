package cn.boommanpro.springbootlog4j2;

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
public class SpringBootLog4j2Application {

    public static void main(String[] args) {

        SpringApplication.run(SpringBootLog4j2Application.class, args);
        log.info("Spring Boot项目启动成功{}",SpringBootLog4j2Application.class);
    }

}
