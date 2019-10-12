package com.webbdealer.dms.infrastructure.dealer.persistence.repositories;

import com.webbdealer.dms.infrastructure.dealer.persistence.entities.Vehicle;
import org.springframework.data.repository.CrudRepository;

public interface VehicleRepository extends CrudRepository<Vehicle, Long> {

    //List<CatalogVehicle> findByShortVin(String shortVin);

    //List<CatalogVehicle> findByYear(String year);

}
