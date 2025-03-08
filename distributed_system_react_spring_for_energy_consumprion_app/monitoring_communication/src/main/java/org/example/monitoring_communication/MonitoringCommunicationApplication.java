package org.example.monitoring_communication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MonitoringCommunicationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitoringCommunicationApplication.class, args);
    }

}
