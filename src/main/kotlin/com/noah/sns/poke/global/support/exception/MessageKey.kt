package com.noah.sns.poke.global.support.exception

enum class MessageKey(val messageSourceKey: String) {
     EXCEPTION_NO_MESSAGE(messageSourceKey = "exception.no.message"),
     EXCEPTION(messageSourceKey = "exception"),

     ALREADY_EXIST_EMAIL(messageSourceKey = "already.exist.email"),
     INVALID_EMAIL_FORM(messageSourceKey = "invalid.email.form"),
     INVALID_PASSWORD_FORM(messageSourceKey = "invalid.password.form"),
     INVALID_NAME_LENGTH(messageSourceKey = "invalid.name.length"),
     INVALID_USER_INFO(messageSourceKey = "invalid.user.info"),

     USER_NOT_FOUND(messageSourceKey = "user.not.found"),
     BLANK_EMAIL_OR_PASSWORD(messageSourceKey = "blank.email.or.password"),
}