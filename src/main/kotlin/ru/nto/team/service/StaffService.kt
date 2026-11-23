package ru.nto.team.service

import org.springframework.stereotype.Service
import ru.nto.team.exceptions.InvalidCodeFormatExceptionDomain
import ru.nto.team.dto.response.ProfileDto
import ru.nto.team.exceptions.UnauthorizedExceptionDomain
import ru.nto.team.repository.StaffRepository

@Service
class StaffService(private val staffRepository: StaffRepository) {
    fun profile(code: String): ProfileDto {
        if (!Regex("^[A-Za-z0-9]{4}$").matches(code)) {
            throw InvalidCodeFormatExceptionDomain()
        }
        val staff = staffRepository.findByCode(code)
        if(staff != null) {
            return ProfileDto(staff.fullName)
        }
        throw UnauthorizedExceptionDomain()
    }
}