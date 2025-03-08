package org.example;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final String QUEUE_NAME = "sensor_data_queue"; // Set your queue name
    private static final String FILE_PATH = "src/sensor.csv"; // Path to the sensor file

    public static void main(String[] args) {

        // Verificăm dacă un `deviceId` a fost transmis ca parametru la rulare
        if (args.length < 1) {
            System.err.println("Trebuie să furnizați un deviceId ca parametru.");
            return;
        }

        // Convertim deviceId-ul din argumentele de rulare într-un Integer
        Integer deviceId;
        try {
            deviceId = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("Device ID trebuie să fie un număr întreg valid.");
            return;
        }

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("turkey.rmq.cloudamqp.com"); // Set RabbitMQ server host
        factory.setPort(5672);
        factory.setVirtualHost("wjhkaqxt");
        factory.setUsername("wjhkaqxt");
        factory.setPassword("AdrFC1eDfi_OVXh84h4OmVwLerTOcsOh");


        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    //double measurementValue = Double.parseDouble(line.trim()); // Read measurement value from file
                    String value=line.trim();
                    long timestamp = Instant.now().toEpochMilli();

                    // Manually create JSON string
                    String message = String.format(
                            "{\"timestamp\": %d, \"device_id\": %d, \"measurement_value\": %s}",
                            timestamp, deviceId, value
                    );

                    channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                    System.out.println("Sent message: " + message);

                    // Wait for 1 minute before reading the next line (simulate 1-minute interval)
                    Thread.sleep(6000);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}