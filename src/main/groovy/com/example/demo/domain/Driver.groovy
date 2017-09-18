package com.example.demo.domain

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@CompileStatic
@EqualsAndHashCode(includes=['name'])
@ToString(includes=['name'], includeNames=true, includePackage=false)
@Entity
class Driver implements Serializable {
    private static final long serialVersionUID = 1

    @Id
    @GeneratedValue
    Long id

    String name

    String username

    @Column(name = '[password]')
    String password

    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    @OneToMany
    Set<Vehicle> vehicles = new HashSet<>()

}
