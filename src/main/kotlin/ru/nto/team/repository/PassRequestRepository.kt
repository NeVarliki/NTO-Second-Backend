package ru.nto.team.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.nto.team.entity.PassRequest
import ru.nto.team.enums.RequestStatus
import ru.nto.team.entity.Staff

interface PassRequestRepository: JpaRepository<PassRequest, Long>{
    fun findAllByHostAndStatus(host: Staff, status: RequestStatus): List<PassRequest>
    fun findAllByHost(host: Staff): List<PassRequest>
    fun findByIdAndHost(id: Long, host: Staff): PassRequest?
    fun findByIdAndHostAndStatus(id: Long, host: Staff, status: RequestStatus): PassRequest?
    fun findByIdAndStatus(id: Long, status: RequestStatus): PassRequest?
}
