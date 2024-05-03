package com.noah.sns.poke.business.user.interfaces.request

import com.noah.sns.poke.global.support.exception.MessageKey
import com.noah.sns.poke.global.support.exception.MethodArgumentInvalidException
import com.noah.sns.poke.global.support.util.isValidPassword

data class UpdateUserRequest(
    val name: String? = null,
    val password: String? = null
) {
    fun validate() {
        if (name != null && (this.name.length < 2 || this.name.length > 10)) {
            throw MethodArgumentInvalidException(MessageKey.INVALID_NAME_LENGTH)
        }

        if (password != null && !this.password.isValidPassword()) {
            throw MethodArgumentInvalidException(MessageKey.INVALID_PASSWORD_FORM)
        }
    }
}