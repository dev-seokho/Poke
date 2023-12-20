package com.noah.sns.poke.global.support.util


private val EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$".toRegex()

// 비밀번호 (숫자, 문자, 특수문자 포함 8~15자리 이내)
private val PASSWORD_REGEX = "^.*(?=^.{8,15}\$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#\$%^&+=]).*\$".toRegex()

fun String.isValidEmail(): Boolean {
    return EMAIL_REGEX.matches(this)
}

fun String.isValidPassword(): Boolean {
    return PASSWORD_REGEX.matches(this)
}