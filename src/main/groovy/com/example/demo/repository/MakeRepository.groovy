package com.example.demo.repository

import com.example.demo.domain.Make
import org.springframework.data.jpa.repository.JpaRepository

interface MakeRepository extends JpaRepository<Make, Long> {

}