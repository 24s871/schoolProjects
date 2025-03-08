package org.example.backend_device.exception;

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(Integer device_id) {
            super("Device with id " + device_id + " not found");

    }
}
