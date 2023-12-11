package com.noah.sns.poke.global.support.exception

import com.noah.sns.poke.global.support.MessageKey
import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Service

@Service
class MessageSourceService(
    private val messageSource: MessageSource,
    private val titleSource: MessageSource
) {
    fun getTitle(messageKey: MessageKey, args: Array<*>? = null): String? {
        return try {
            titleSource.getMessage(messageKey.messageSourceKey, args, LocaleContextHolder.getLocale())
        } catch (e: NoSuchMessageException) {
            null
        }
    }

    fun getMessage(messageKey: MessageKey, args: Array<*>? = null): String {
        return try {
            messageSource.getMessage(messageKey.messageSourceKey, args, LocaleContextHolder.getLocale())
        } catch (e: NoSuchMessageException) {
            messageSource.getMessage(
                MessageKey.EXCEPTION_NO_MESSAGE.messageSourceKey,
                args,
                LocaleContextHolder.getLocale()
            )
        }
    }
}