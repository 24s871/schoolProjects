package org.example.backend_device.controller;


import org.example.backend_device.exception.DeviceNotFoundException;
import org.example.backend_device.model.Device;
import org.example.backend_device.repository.DeviceRepository;
import org.example.backend_device.service.JwtService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;



import java.util.List;


@RestController
@CrossOrigin(origins={"http://localhost:3000","http://localhost:8083"},allowCredentials = "true")
@EnableWebSecurity
public class DeviceController {


    //jdbc:mysql://mysql_dispozitive:3306/Dispozitive

    @Autowired
    private DeviceRepository deviceRepository;


    private final RabbitTemplate rabbitTemplate;
    private final JwtService jwtService;

    @Autowired
    public DeviceController(RabbitTemplate rabbitTemplate, JwtService jwtService) {
        this.rabbitTemplate = rabbitTemplate;
        this.jwtService = jwtService;
    }


    @PostMapping("/device")
    ResponseEntity<?> newDevice(@RequestBody Device newDevice,@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    { // make a class that decodes my town and request a http header along w it
        System.out.println("token: "+authorizationHeader);
       try {
           // Extract JWT token from the Authorization header

           String token = authorizationHeader.replace("Bearer ", "");
           System.out.println("token: "+token);
           // Decode and validate the token
           String role = jwtService.extractClaim(token, claims -> claims.get("role", String.class));
           System.out.println("Role: " + role);
           if (!"ADMIN".equals(role)) {
               return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Only ADMIN users can perform this action.");
           }

           Device dev = deviceRepository.save(newDevice);

           String message = String.format("device_id: %d,user_id: %d,mhec: %s,topic: %s", newDevice.getDevice_id(), newDevice.getUser_id(), newDevice.getMhec(), "create");
           rabbitTemplate.convertAndSend("device.monitoring.queue", message);
           System.out.println("Message sent(create): " + message);

           //return dev;
           return ResponseEntity.status(HttpStatus.OK).body("device created");
       }
       catch (Exception e)
       {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token: " + e.getMessage());
       }

    }


    @GetMapping("/all_devices")
    @PreAuthorize("hasRole('ADMIN')")
    List<Device> getAllDevices()
    {
        return deviceRepository.findAll();
    }

    @GetMapping("/device/{device_id}")
    Device getDeviceById(@PathVariable Integer device_id)
    {
        return deviceRepository.findById(device_id).orElseThrow(()->new DeviceNotFoundException(device_id));
    }

    @PutMapping("/device/{device_id}")
    @PreAuthorize("hasRole('ADMIN')")
    Device updateDevice(@RequestBody Device newDevice, @PathVariable Integer device_id)
    {
        return deviceRepository.findById(device_id).map(
                device -> {
                    device.setDescription(newDevice.getDescription());
                    device.setAddress(newDevice.getAddress());
                    device.setMhec(newDevice.getMhec());
                    device.setUser_id(newDevice.getUser_id());

                    String message = String.format("device_id: %d,user_id: %d,mhec: %s,topic: %s",device_id,newDevice.getUser_id(),newDevice.getMhec(),"update device");
                    rabbitTemplate.convertAndSend("device.monitoring.queue", message);
                    System.out.println("Message sent(device update): " + message);

                    return deviceRepository.save(device);
                }
        ).orElseThrow(()->new DeviceNotFoundException(device_id));
    }

    @DeleteMapping("/device/{device_id}")
    @PreAuthorize("hasRole('ADMIN')")
    String deleteDevice(@PathVariable Integer device_id)
    {
        if(!deviceRepository.existsById(device_id))
        {
            throw new DeviceNotFoundException(device_id);
        }
        deviceRepository.deleteById(device_id);

        String message = String.format("id: %d,topic: %s",device_id,"delete");
        rabbitTemplate.convertAndSend("device.monitoring.queue", message);
        System.out.println("Message sent(delete): " + message);

        return "Device with id " + device_id + " was deleted";
    }

    @GetMapping("/{user_id}/devices")
    List<Device> getAllDevicesByUser(@PathVariable Integer user_id)
    {
        return deviceRepository.findAllByUser_id(user_id);
    }

    @DeleteMapping("/user/{user_id}/devices")
    @PreAuthorize("hasRole('ADMIN')")
    String deleteDevicesByUser(@PathVariable Integer user_id)
    {
        List<Device> dev= deviceRepository.findAllByUser_id(user_id);
        if(!dev.isEmpty()) {
            deviceRepository.deleteAll(dev);

            String message = String.format("id: %d,topic: %s",user_id,"delete by user");
            rabbitTemplate.convertAndSend("device.monitoring.queue", message);
            System.out.println("Message sent(delete by user): " + message);

            return "Devices with id " + user_id + " was deleted";
        }
        else return "Devices with id " + user_id + " don't exist";

    }


}
