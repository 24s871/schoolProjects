package org.example.monitoring_communication.connection;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue deviceQueue() {
        return new Queue("device.monitoring.queue", true);
    }

    @Bean
    public Queue deviceQueue2() {
        return new Queue("sensor_data_queue", true);
    }
}
