package org.example.monitoring_communication.service;

import org.springframework.stereotype.Service;


@Service
public class EnergyConsumption {

    public double calculateEnergyConsumption(double oldNumber,double newNumber)
    {
        return newNumber-oldNumber;
    }



}
