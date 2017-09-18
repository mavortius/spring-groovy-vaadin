package com.example.demo.repository

import com.example.demo.domain.Vehicle
import org.springframework.data.jpa.repository.JpaRepository

interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}