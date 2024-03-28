package org.haolin;


import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.haolin.mailService.MailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class MyApplication {

    private static final Logger logger = LogManager.getLogger(MyApplication.class);

    public static void main(String[] args) {
        log.info("log应用启动！");
        logger.info("logger应用启动！");
        SpringApplication.run(MyApplication.class, args);
    }



}
