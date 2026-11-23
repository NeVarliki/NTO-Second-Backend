package ru.nto.team.dto.response

import ru.nto.team.enums.RequestStatus
import java.time.LocalDateTime

data class RequestListDto(val id: Long, val fullName: String, val validFrom: LocalDateTime,
                          val validTo: LocalDateTime, val status: RequestStatus)
