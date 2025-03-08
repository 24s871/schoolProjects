package org.example.monitoring_communication.repository;

import org.example.monitoring_communication.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TrackRepository extends JpaRepository<Track, Integer> {

    @Query(value = "select * from track where device_id=?",nativeQuery = true)
    List<Track> findAllByDevice_id(Integer device_id);

    @Query(value = "select * from track where user_id=?",nativeQuery = true)
    List<Track> findAllByUser_id(Integer user_id);

    @Query(value = "select * from track where user_id=? and  moment between ? and ? and topic='update consumption';",nativeQuery = true)
    List<Track> findByUser_idAndMomentBetweenAndTopic(Integer user_id, LocalDateTime startOfDay, LocalDateTime endOfDay);

    @Query(value = "select * from track where user_id=? and topic='update consumption'",nativeQuery = true)
    List<Track> findAllByUser_idAndTopic(Integer user_id);


}


