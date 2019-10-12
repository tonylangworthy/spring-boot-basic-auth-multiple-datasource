package com.webbdealer.dms.presentation.controllers;

import com.webbdealer.dms.infrastructure.dealer.persistence.entities.Vehicle;
import com.webbdealer.dms.infrastructure.dealer.persistence.repositories.VehicleRepository;
import com.webbdealer.dms.infrastructure.dealer.persistence.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/vehicles")
    public ResponseEntity<Iterable<Vehicle>> getAllVehicles() {
        Iterable<Vehicle> vehicleCollection = vehicleService.findAllVehicles();
        return ResponseEntity.ok(vehicleCollection);
    }


//    @GetMapping("/vehicle")
//    public ResponseEntity<Iterable<CatalogVehicle>> getCatalogVehicles() {
//        return ResponseEntity.ok(catalogVehicleRepository.findAll());
//    }
//
//    @GetMapping("/vehicle/{id}")
//    public ResponseEntity<CatalogVehicle> getCatalogVehicleById(@PathVariable Long id) {
//        Optional<CatalogVehicle> optionalCatalogVehicle = catalogVehicleRepository.findById(id);
//        //System.out.println(optionalCatalogVehicle.isPresent());
//        if(optionalCatalogVehicle.isPresent()) {
//            return ResponseEntity.ok(optionalCatalogVehicle.get());
//        }
//        return ResponseEntity.notFound().build();
//    }
//
//    @RequestMapping(value = "/vehicle", method = {RequestMethod.POST, RequestMethod.PUT})
//    public ResponseEntity<?> createCatalogVehicle(@Valid @RequestBody CatalogVehicle catalogVehicle, Errors errors) {
//        if(errors.hasErrors()) {
//            return ResponseEntity.badRequest().body(VehicleValidationErrorBuilder.fromBindingErrors(errors));
//        }
//
//        CatalogVehicle result = catalogVehicleRepository.save(catalogVehicle);
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(result.getId()).toUri();
//
//        return ResponseEntity.created(location).build();
//    }
//
//    // overloaded methods
//
//    // this one accepts id
//    @DeleteMapping("/vehicle/{id}")
//    public ResponseEntity<CatalogVehicle> deleteCatalogVehicle(@PathVariable Long id) {
//        catalogVehicleRepository.delete(VehicleBuilder.create().withId(id).build());
//        return ResponseEntity.noContent().build();
//    }
//
//    // this one accepts instance
//    @DeleteMapping("/vehicle")
//    public ResponseEntity<CatalogVehicle> deleteCatalogVehicle(@RequestBody CatalogVehicle catalogVehicle) {
//        catalogVehicleRepository.delete(catalogVehicle);
//        return ResponseEntity.noContent().build();
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    public VehicleValidationError handleException(Exception exception) {
//        return new VehicleValidationError(exception.getMessage());
//    }

}
