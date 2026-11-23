package ru.nto.team.validation

import ru.nto.team.CreateRequestBody
import ru.nto.team.DecisionBody
import ru.nto.team.enums.RequestStatus
import ru.nto.team.exceptions.InvalidStatusDecisionExceptionDomain
import ru.nto.team.exceptions.ValidationException
import java.time.LocalDateTime

// TODO: normalization, strict date parsing and collecting all available field errors.
object RequestValidation{

    fun normalizeRequest(request: CreateRequestBody): CreateRequestBody{
        return CreateRequestBody(
            request.fullName?.trim(),
            request.phone?.trim(),
            request.organization?.trim(),
            request.purpose?.trim(),
            request.validFrom?.trim(),
            request.validTo?.trim()
        )
    }

    fun normalizeDecision(request: DecisionBody): DecisionBody {
        return DecisionBody(request.status?.trim(), request.comment?.trim())
    }

    fun checkRequest(request: CreateRequestBody, now: LocalDateTime){
        val reasons: MutableMap<String, String> = mutableMapOf()
        reasons.putAll(checkFullName(request.fullName))
        reasons.putAll(checkPhone(request.phone))
        reasons.putAll(checkOrganization(request.organization))
        reasons.putAll(checkPurpose(request.purpose))
        reasons.putAll(checkValidationPeriod(request.validFrom, request.validTo, now))
        if(reasons.isNotEmpty()) {
            throw ValidationException(reasons)
        }
    }
    fun checkDecision(request: DecisionBody){
        val reasons: MutableMap<String, String> = mutableMapOf()
        try{
            val parsedStatus: RequestStatus = RequestStatus.valueOf(request.status!!)
            if(parsedStatus != RequestStatus.REJECTED && parsedStatus != RequestStatus.APPROVED){
                throw InvalidStatusDecisionExceptionDomain()
            } else if(parsedStatus == RequestStatus.REJECTED && (request.comment == null || request.comment.isEmpty() || request.comment.length > 500)){
                reasons.put("comment", "Invalid comment length")
            }
        } catch (_: RuntimeException){
            reasons.put("status", "Status parse error")
        }
        if(reasons.isNotEmpty()) {
            throw ValidationException(reasons)
        }
    }


    private fun checkFullName(fullName: String?): Map<String, String> {
        if(fullName == null || fullName.length < 5 || fullName.length > 150){
            return mapOf("fullName" to "Invalid length of full name")
        }
        return mapOf()
    }

    private fun checkPhone(phone: String?): Map<String, String> {
        if(phone == null || !Regex("^\\+7[0-9]{10}$").matches(phone)){
            return mapOf("phone" to "Phone number is invalid")
        }
        return mapOf()
    }
    private fun checkOrganization(organization: String?): Map<String, String> {
        if(organization != null && organization.length > 150){
            return mapOf("organization" to "Organization length is too long")
        }
        return mapOf()
    }
    private fun checkPurpose(purpose: String?): Map<String, String> {
        if(purpose == null || purpose.length < 5 || purpose.length > 200){
            return mapOf("purpose" to "Invalid length of purpose")
        }
        return mapOf()
    }
    private fun checkValidationPeriod(validFrom: String?, validTo: String?, now: LocalDateTime): Map<String, String> {
        val periodReasons: MutableMap<String, String> = mutableMapOf()
        var parsedFrom: LocalDateTime? = null
        var parsedTo: LocalDateTime? = null
        try {
            parsedFrom = LocalDateTime.parse(validFrom!!)
            if(parsedFrom.isBefore(now)){
                periodReasons.put("validFrom", "ValidFrom is before than current moment")
            }
        } catch (_: RuntimeException) {
            periodReasons.put("validFrom", "ValidFrom is incorrect format")
        }
        try {
            parsedTo = LocalDateTime.parse(validTo!!)
            if(parsedFrom != null && !parsedTo.isAfter(parsedFrom)) {
                periodReasons.put("validTo", "ValidTo is not after than validFrom")
            }
            if(parsedFrom != null && !parsedTo.toLocalDate().equals(parsedFrom.toLocalDate())) {
                periodReasons.put("validTo", "ValidTo date is not the same with validFrom")
            }
        } catch (_: RuntimeException) {
            periodReasons.put("validTo", "ValidTo is incorrect format")
        }
        return periodReasons
    }
    fun parseDate(date: String): LocalDateTime {
        val parsedDate = LocalDateTime.parse(date)
        return parsedDate.minusNanos(parsedDate.nano.toLong())
    }
}