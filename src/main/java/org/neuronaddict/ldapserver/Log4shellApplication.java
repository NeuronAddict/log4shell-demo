package org.neuronaddict.ldapserver;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
public class Log4shellApplication {

    private static final Logger logger = LogManager.getLogger(Log4shellApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(Log4shellApplication.class, args);
        logger.info("jvm version = ${java:version}");
    }

}
