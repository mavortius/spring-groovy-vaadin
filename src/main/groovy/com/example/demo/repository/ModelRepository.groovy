package com.example.demo.repository

import com.example.demo.domain.Model
import org.springframework.data.jpa.repository.JpaRepository

interface ModelRepository extends JpaRepository<Model, Long> {

}