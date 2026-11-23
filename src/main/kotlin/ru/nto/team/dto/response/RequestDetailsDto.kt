package ru.nto.team.dto.response

import ru.nto.team.enums.RequestStatus
import java.time.LocalDateTime

data class RequestDetailsDto(val id: Long, val fullName: String,
                             val phone: String, val organization: String?,
                             val purpose: String, val validFrom: LocalDateTime,
                             val validTo: LocalDateTime, val status: RequestStatus,
                             val comment: String?, val createdAt: LocalDateTime,
                             val updatedAt: LocalDateTime)