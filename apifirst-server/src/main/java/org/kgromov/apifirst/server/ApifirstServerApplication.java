package org.kgromov.apifirst.server;

import org.kgromov.apifirst.server.config.SpringFrameworkGenerated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApifirstServerApplication {

    @SpringFrameworkGenerated
    public static void main(String[] args) {
        SpringApplication.run(ApifirstServerApplication.class, args);
    }

}