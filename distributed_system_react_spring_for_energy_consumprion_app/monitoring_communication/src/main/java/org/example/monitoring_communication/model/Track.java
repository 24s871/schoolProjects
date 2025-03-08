package org.example.monitoring_communication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="track")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Track {

    @Id
    @GeneratedValue
    private Integer track_id;
    private Integer device_id;
    private Integer user_id;
    private LocalDateTime moment;
    private double consumption;
    private double current_value;
    private String topic;
    private String mhec;

}
