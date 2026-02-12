package com.github.terrakok.mobicon.logger

import org.koin.core.annotation.Single

@Single
internal class WasmJsLogger : Logger {
    override fun debug(tag: String, message: String) {
        println("[$tag] DEBUG: $message")
    }

    override fun info(tag: String, message: String) {
        println("[$tag] INFO: $message")
    }

    override fun warning(tag: String, message: String) {
        println("[$tag] WARN: $message")
    }

    override fun error(tag: String, message: String, throwable: Throwable?) {
        println("[$tag] ERROR: $message")
        //throwable?.let { console.error(it) }
    }
}
