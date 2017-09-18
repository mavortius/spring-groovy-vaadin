package com.example.demo

import com.example.demo.domain.Driver
import com.example.demo.domain.Make
import com.example.demo.domain.Model
import com.example.demo.domain.Vehicle
import com.example.demo.repository.DriverRepository
import com.example.demo.repository.MakeRepository
import com.example.demo.repository.ModelRepository
import com.example.demo.repository.VehicleRepository
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component

@Slf4j
@Component
class DataLoader {

    DriverRepository driverRepository
    MakeRepository makeRepository
    ModelRepository modelRepository
    VehicleRepository vehicleRepository

    DataLoader(DriverRepository driverRepository, MakeRepository makeRepository,
               ModelRepository modelRepository, VehicleRepository vehicleRepository) {
        this.driverRepository = driverRepository
        this.makeRepository = makeRepository
        this.modelRepository = modelRepository
        this.vehicleRepository = vehicleRepository
    }

    void load() {
        log.info "Loading database..."

        final driver1 = new Driver(name: "Susan", username: "susan", password: "password1")
        final driver2 = new Driver(name: "Pedro", username: "pedro", password: "password2")
        driverRepository.save([driver1, driver2])

        final nissan = new Make(name: "Nissan")
        final ford = new Make(name: "Ford")
        makeRepository.save([nissan, ford])

        final titan = new Model(name: "Titan")
        final leaf = new Model(name: "Leaf")
        final windstar = new Model(name: "Windstar")
        modelRepository.save([titan, leaf, windstar])

        vehicleRepository.save([
                new Vehicle(name: "Pickup", driver: driver1, make: nissan, model: titan),
                new Vehicle(name: "Economy", driver: driver1, make: nissan, model: leaf),
                new Vehicle(name: "Minivan", driver: driver2, make: ford, model: windstar),
        ])
    }
}
