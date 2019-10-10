package cn.boommanpro.springbootlogback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:boommanpro@gmail.com">BoomManPro</a>
 * @date 2019/10/10 20:27
 * @created by BoomManPro
 */
@Slf4j
@RequestMapping
@RestController
public class UpdateLoggerLevelController {

    private final LoggingSystem loggingSystem;

    private boolean status=false;

    public UpdateLoggerLevelController(LoggingSystem loggingSystem) {
        this.loggingSystem = loggingSystem;
    }

    @GetMapping
    public String update(){
        log.debug("123");
        if (status) {
            loggingSystem.setLogLevel("cn.boommanpro.springbootlogback", LogLevel.INFO);
        }else {
            loggingSystem.setLogLevel("cn.boommanpro.springbootlogback", LogLevel.DEBUG);
        }
        status = !status;
        return status+"";
    }

}
