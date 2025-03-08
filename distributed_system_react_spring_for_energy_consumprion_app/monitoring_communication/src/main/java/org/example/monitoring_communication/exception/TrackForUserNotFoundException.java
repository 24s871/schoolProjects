package org.example.monitoring_communication.exception;

public class TrackForUserNotFoundException extends RuntimeException {
    public TrackForUserNotFoundException(Integer id) {
        super("Could not find track with user id " + id);
    }
}
