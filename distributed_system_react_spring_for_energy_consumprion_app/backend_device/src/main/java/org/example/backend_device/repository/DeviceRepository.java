package org.example.backend_device.repository;

import org.example.backend_device.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
    @Query(value = "select * from device where user_id=?",nativeQuery = true)
    List<Device> findAllByUser_id(Integer user_id);
}
