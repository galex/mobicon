package com.github.terrakok.mobicon.logger

import org.koin.core.annotation.Single

@Single
internal class JsLogger : Logger {
    override fun debug(tag: String, message: String) {
        console.log("[$tag] DEBUG: $message")
    }

    override fun info(tag: String, message: String) {
        console.info("[$tag] INFO: $message")
    }

    override fun warning(tag: String, message: String) {
        console.warn("[$tag] WARN: $message")
    }

    override fun error(tag: String, message: String, throwable: Throwable?) {
        console.error("[$tag] ERROR: $message")
        throwable?.let { console.error(it) }
    }
}
