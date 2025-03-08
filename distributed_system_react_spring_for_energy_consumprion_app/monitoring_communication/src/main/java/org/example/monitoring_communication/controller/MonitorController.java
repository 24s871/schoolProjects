package org.example.monitoring_communication.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.monitoring_communication.exception.TrackForDeviceNotFoundException;
import org.example.monitoring_communication.exception.TrackForUserNotFoundException;
import org.example.monitoring_communication.model.Track;
import org.example.monitoring_communication.repository.TrackRepository;
import org.example.monitoring_communication.service.EnergyConsumption;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins={"http://localhost:3000","http://localhost:8081","http://localhost:8083"},allowCredentials = "true")
public class MonitorController {

    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private EnergyConsumption energyConsumption;



    //jdbc:mysql://localhost:3306/Monitor

    @RabbitListener(queues = "device.monitoring.queue")
    public void selection(String message)
    {
        System.out.println("Received message: " + message);
        String[] tokens = message.split(",");
        int n= tokens.length;
        if(n==4) {
            Integer device_id = null;
            Integer user_id = null;
            String mhec = null;
            String topic = null;

            for (String token : tokens) {
                String[] pair = token.split(":");
                if (pair[0].trim().equals("device_id")) {
                    device_id = Integer.parseInt(pair[1].trim());
                } else if (pair[0].trim().equals("user_id")) {
                    user_id = Integer.parseInt(pair[1].trim());
                } else if (pair[0].trim().equals("mhec")) {
                    mhec = pair[1].trim();
                } else if (pair[0].trim().equals("topic")) {
                    topic = pair[1].trim();
                }
            }

            if(topic!=null){
                if(topic.equals("create")) {
                saveTrack(device_id,user_id,mhec);}
                else if(topic.equals("update device")) {
                    updateTrackForDevice(device_id,user_id,mhec);
                }
            }

        }
        else
        {
            Integer id = null;
            String topic = null;

            for (String token : tokens) {
                String[] pair = token.split(":");
                if (pair[0].trim().equals("id")) {
                    id = Integer.parseInt(pair[1].trim());
                } else if (pair[0].trim().equals("topic")) {
                    topic = pair[1].trim();
                }
            }
            if(topic!=null){
                if(topic.equals("delete")) {
                    deleteTrack(id);
                }
                else if(topic.equals("delete by user")) {
                    deleteTrackByUser(id);
                }
            }
        }
    }


    @PutMapping("/monitor/newtrack")
    public void saveTrack(Integer device_id, Integer user_id, String mhec)
    {
            Track newTrack = new Track();
            newTrack.setConsumption(0);
            newTrack.setMoment(LocalDateTime.now());
            newTrack.setDevice_id(device_id);
            newTrack.setUser_id(user_id);
            newTrack.setCurrent_value(0);
            newTrack.setMhec(mhec);
            newTrack.setTopic("create");
            trackRepository.save(newTrack);
            System.out.println("New track saved");

    }


    @PutMapping("/monitor/devicetrack")
    public void updateTrackForDevice(Integer device_id, Integer user_id, String mhec)
    {

            List<Track> T = trackRepository.findAllByDevice_id(device_id);
            for (Track t : T) {
                t.setUser_id(user_id);
                t.setMhec(mhec);
                t.setTopic("update device");
                trackRepository.save(t);
            }
    }


    @RabbitListener(queues = "sensor_data_queue")
    @PutMapping("/monitor/updatetrack")
    public void updateTrack(String message) {
        try {
            System.out.println("Received message: " + message);
            // Use ObjectMapper to parse the JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(message);

            // Extract values from JSON
            long timestamp = rootNode.get("timestamp").asLong();
            int id = rootNode.get("device_id").asInt();
            double newValue = rootNode.get("measurement_value").asDouble();


            // Transformăm timestamp-ul în LocalDateTime
            LocalDateTime moment = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();


            // Găsim track-ul vechi și creăm unul nou
            List<Track> T = trackRepository.findAllByDevice_id(id);
            Track oldTrack = T.getLast();
            Track newTrack = new Track();
            newTrack.setDevice_id(id);
            newTrack.setUser_id(oldTrack.getUser_id());
            newTrack.setMoment(moment);
            newTrack.setCurrent_value(newValue);
            newTrack.setMhec(oldTrack.getMhec());
            newTrack.setTopic("update consumption");
            System.out.println("new track\n");
            // Calculăm consumul
            double cons = energyConsumption.calculateEnergyConsumption(oldTrack.getCurrent_value(), newValue);
            System.out.println("cons: " + cons + "\n");
            newTrack.setConsumption(cons);

            // Salvăm track-ul nou
            trackRepository.save(newTrack);

        } catch (Exception e) {
            System.err.println("Eroare la procesarea mesajului: " + e.getMessage());
        }
    }



    @DeleteMapping("/monitor/delete")
    public void deleteTrack(Integer device_id)
    {

            List<Track> t = trackRepository.findAllByDevice_id(device_id);
            if (t.isEmpty()) {
                throw new TrackForDeviceNotFoundException(device_id);
            }
            trackRepository.deleteAll(t);
            System.out.println("Tracks deleted successfully for device " + device_id);

    }



    @DeleteMapping("/monitor/deletebyuser")
    public void deleteTrackByUser(Integer user_id)
    {

            List<Track> t = trackRepository.findAllByUser_id(user_id);
            if (t.isEmpty()) {
                throw new TrackForUserNotFoundException(user_id);
            }
            trackRepository.deleteAll(t);
            System.out.println("Tracks deleted successfully for user " + user_id);

    }



    @GetMapping("/{user_id}")
    public ResponseEntity<Map<Integer, List<Track>>> getTracksForUser(@PathVariable Integer user_id,
            @RequestParam String date) {

        // Convert the date string to a LocalDateTime range
        LocalDate selectedDate = LocalDate.parse(date);
        LocalDateTime startOfDay = selectedDate.atStartOfDay();
        LocalDateTime endOfDay = selectedDate.atTime(23, 59, 59);
        System.out.println("date: " + selectedDate + " start: " + startOfDay + " end: " + endOfDay);

        // Fetch all tracks for the user and day
        List<Track> tracks = trackRepository.findByUser_idAndMomentBetweenAndTopic(user_id, startOfDay, endOfDay);
        System.out.println("Found tracks:\n");
        for (Track track : tracks) {
            System.out.println(track.getTrack_id()+" "+track.getMoment()+" "+track.getCurrent_value());
        }

        if (tracks.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyMap()); // Return empty map if no data found
        }

        // Group by device_id
        Map<Integer, List<Track>> groupedByDevice = tracks.stream()
                .collect(Collectors.groupingBy(Track::getDevice_id));

        return ResponseEntity.ok(groupedByDevice);
    }


}
