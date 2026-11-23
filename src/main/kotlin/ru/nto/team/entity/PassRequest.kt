package ru.nto.team.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import ru.nto.team.enums.RequestStatus
import ru.nto.team.validation.RequestValidation
import java.time.LocalDateTime

// TODO: JPA entity for pass_request, including a staff association and generated Long ID.
// No @Entity yet: the unfinished model must not prevent application startup.
@Entity
@Table(name = "pass_request")
class PassRequest(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @ManyToOne
    @JoinColumn(name = "host_id")
    var host: Staff = Staff(),
    @Column(nullable = false)
    var fullName: String = "",
    @Column(nullable = false)
    var phone: String = "",
    @Column
    var organization: String? = null,
    @Column(nullable = false)
    var purpose: String = "",
    @Column(nullable = false)
    var validFrom: LocalDateTime = LocalDateTime.now(),
    @Column(nullable = false)
    var validTo: LocalDateTime = LocalDateTime.now(),
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    var status: RequestStatus = RequestStatus.PENDING,
    @Column
    var comment: String? = null,
    @Column(nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)