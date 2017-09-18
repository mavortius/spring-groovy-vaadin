package com.example.demo.repository

import com.example.demo.domain.Driver
import org.springframework.data.jpa.repository.JpaRepository

interface DriverRepository extends JpaRepository<Driver, Long> {

}