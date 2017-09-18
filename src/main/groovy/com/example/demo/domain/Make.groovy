package com.example.demo.domain

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@CompileStatic
@EqualsAndHashCode(includes = ['name'])
@ToString(includes=['name'], includeNames=true, includePackage=false)
@Entity
class Make {

    @Id
    @GeneratedValue
    Long id

    String name
}
