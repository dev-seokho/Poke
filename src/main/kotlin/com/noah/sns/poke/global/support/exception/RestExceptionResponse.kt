package com.noah.sns.poke.global.support.exception

data class RestExceptionResponse(
    val status: Int,
    val code: String,
    val title: String?,
    val message: String?,
    val messageArguments: List<*>?,
) {
    companion object {
        fun of(
            status: Int,
            code: String,
            message: String?,
            title: String? = null,
            messageArguments: Array<*>? = null,
        ): RestExceptionResponse {
            return RestExceptionResponse(
                status = status,
                code = code,
                title = title,
                message = message,
                messageArguments = messageArguments?.toList(),
            )
        }
    }
}