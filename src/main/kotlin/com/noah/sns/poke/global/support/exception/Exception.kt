package com.noah.sns.poke.global.support.exception

import com.noah.sns.poke.global.support.MessageKey

abstract class ApiException : RuntimeException {
    val messageKey: MessageKey
    val objs: Array<*>
    var messageArguments: Array<*>? = null

    constructor(messageKey: MessageKey, vararg objs: Any) : super(
        messageKey.name
    ) {
        this.messageKey = messageKey
        this.objs = objs
    }

    constructor(messageKey: MessageKey, cause: Throwable, vararg objs: Any) : super(
        messageKey.name, cause
    ) {
        this.messageKey = messageKey
        this.objs = objs
    }

    override val message: String?
        get() = "${super.message}:${objs.joinToString { it.toString() }}${
            messageArguments?.joinToString(
                prefix = ", "
            ) { it.toString() } ?: ""
        }"
}


class MethodArgumentInvalidException : ApiException {
    constructor(messageKey: MessageKey, vararg objs: Any) : super(messageKey, *objs)
    constructor(messageKey: MessageKey, cause: Throwable, vararg objs: Any) : super(
        messageKey,
        cause,
        *objs
    )
}

class EntityNotFoundException : ApiException {
    constructor(messageKey: MessageKey, vararg objs: Any) : super(messageKey, *objs)
    constructor(messageKey: MessageKey, cause: Throwable, vararg objs: Any) : super(
        messageKey,
        cause,
        *objs
    )
}