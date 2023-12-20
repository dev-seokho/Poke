package com.noah.sns.poke.global.support.exception

data class RestExceptionResponse(
    val status: Int,
    val code: String,
    val message: String?,
) {
    companion object {
        fun of(
            status: Int,
            code: String,
            message: String?
        ): RestExceptionResponse {
            return RestExceptionResponse(
                status = status,
                code = code,
                message = message
            )
        }
    }
}