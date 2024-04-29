package com.noah.sns.poke.global.support.exception

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class RestExceptionHandler(
    private val messageSourceService: MessageSourceService
) : ResponseEntityExceptionHandler() {

    private val log: Logger = LoggerFactory.getLogger(RestExceptionHandler::class.java)

    @ExceptionHandler(EntityNotFoundException::class)
    fun handle(e: EntityNotFoundException, request: HttpServletRequest): ResponseEntity<Any>? {
        return handleExceptionInternal(
            e,
            RestExceptionResponse.of(
                status = HttpStatus.NOT_FOUND.value(),
                code = e.messageKey.name,
                message = messageSourceService.getMessage(e.messageKey, e.messageArguments)
            ),
            HttpHeaders.EMPTY,
            HttpStatus.NOT_FOUND,
            ServletWebRequest(request)
        )
    }

    @ExceptionHandler(MethodArgumentInvalidException::class)
    fun handle(e: MethodArgumentInvalidException, request: HttpServletRequest): ResponseEntity<Any>? {
        return handleExceptionInternal(
            e,
            RestExceptionResponse.of(
                status = HttpStatus.BAD_REQUEST.value(),
                code = e.messageKey.name,
                message = messageSourceService.getMessage(e.messageKey, e.messageArguments)
            ),
            HttpHeaders.EMPTY,
            HttpStatus.BAD_REQUEST,
            ServletWebRequest(request)
        )
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handle(e: BadCredentialsException, request: HttpServletRequest): ResponseEntity<Any>? {
        return handleExceptionInternal(
            e,
            RestExceptionResponse.of(
                status = HttpStatus.BAD_REQUEST.value(),
                code = MessageKey.INVALID_USER_INFO.name,
                message = messageSourceService.getMessage(MessageKey.INVALID_USER_INFO)
            ),
            HttpHeaders.EMPTY,
            HttpStatus.BAD_REQUEST,
            ServletWebRequest(request)
        )
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handle(e: ConstraintViolationException, request: HttpServletRequest): ResponseEntity<Any>? {
        //MethodArgumentNotValidException 와 다른 점은 find api 의 keyword length 가 0 이라던가 하는 client 가 알 수 있을 때 발생 - 주로 GET
        e.findRootCause().log()
        val errorMessage = e.constraintViolations.joinToString(separator = ",") { it.message }
        return handleExceptionInternal(
            e,
            RestExceptionResponse.of(
                status = HttpStatus.BAD_REQUEST.value(),
                code = MessageKey.EXCEPTION.name,
                message = errorMessage,
            ),
            HttpHeaders.EMPTY,
            HttpStatus.BAD_REQUEST,
            ServletWebRequest(request)
        )!!
    }

    fun <T : Throwable> T.log(): T {
        log.error(this.message, this)
        return this
    }
}

fun Throwable.findRootCause(): Throwable {
    var rootCause = this
    while (rootCause.cause != null && rootCause.cause != rootCause) {
        rootCause = rootCause.cause!!
    }
    return rootCause
}