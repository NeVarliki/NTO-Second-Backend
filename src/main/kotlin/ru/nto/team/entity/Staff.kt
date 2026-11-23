package ru.nto.team.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "staff")
class Staff(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long = 0,
            @Column(unique = true, nullable = false) var code: String = "",
            @Column var fullName: String = "")