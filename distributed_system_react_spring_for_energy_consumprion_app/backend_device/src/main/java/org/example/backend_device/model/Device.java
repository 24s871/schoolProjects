package org.example.backend_device.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="device")
public class Device {
    @Id
    @GeneratedValue
    private Integer device_id;

    private String description;
    private String address;
    private String mhec;
    private Integer user_id;

}
