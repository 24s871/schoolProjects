package org.example.monitoring_communication.exception;

public class TrackForDeviceNotFoundException extends RuntimeException{
    public TrackForDeviceNotFoundException(Integer deviceId) {
        super("Could not find track for device " + deviceId);
    }
}
