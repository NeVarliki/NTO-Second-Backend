package ru.nto.team.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// TODO: implement domain errors and validation errors. This only makes unfinished routes usable.
sealed class DomainApiException(val status: HttpStatus, message: String) : RuntimeException(message)
class ValidationException(val reasons: Map<String, String>): RuntimeException("Invalid request")
class UnauthorizedExceptionDomain: DomainApiException(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.reasonPhrase)
class InvalidCodeFormatExceptionDomain: DomainApiException(HttpStatus.BAD_REQUEST, "Invalid code format")
class InvalidStatusFormatExceptionDomain: DomainApiException(HttpStatus.BAD_REQUEST, "Invalid status format")
class RequestNotFoundExceptionDomain: DomainApiException(HttpStatus.NOT_FOUND, "Request not found")
class DuplicateRequestExceptionDomain: DomainApiException(HttpStatus.CONFLICT, "Duplicate request")
class InvalidStatusDecisionExceptionDomain: DomainApiException(HttpStatus.CONFLICT, "Invalid status transition")
@RestControllerAdvice
class ApiExceptionHandler {
    @ExceptionHandler(DomainApiException::class)
    fun handleDomainApiException(error: DomainApiException): ResponseEntity<Map<String, String>> =
        ResponseEntity.status(error.status).body(mapOf("error" to error.message!!))


    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(error: ValidationException): ResponseEntity<Map<String, Map<String, String>>> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("errors" to error.reasons))


}
