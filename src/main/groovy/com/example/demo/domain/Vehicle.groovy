package com.example.demo.domain

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@CompileStatic
@EqualsAndHashCode(includes=['name', 'make', 'model'])
@ToString(includes=['name', 'make', 'model'], includeNames=true, includePackage=false)
@Entity
class Vehicle {

    @Id
    @GeneratedValue
    Long id

    String name

    @ManyToOne
    Make make

    @ManyToOne
    Model model

    @ManyToOne
    Driver driver

}
