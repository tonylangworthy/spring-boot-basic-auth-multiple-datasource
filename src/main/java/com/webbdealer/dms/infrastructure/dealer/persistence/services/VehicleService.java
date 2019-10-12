package com.webbdealer.dms.infrastructure.dealer.persistence.services;

import com.webbdealer.dms.infrastructure.dealer.persistence.entities.Vehicle;
import com.webbdealer.dms.infrastructure.dealer.persistence.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Iterable<Vehicle> findAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }
}
