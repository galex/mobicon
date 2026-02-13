package com.github.terrakok.mobicon

import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.KoinApplication
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes
import org.koin.plugin.module.dsl.startKoin

@Module
@ComponentScan("com.github.terrakok.mobicon")
@Configuration
class AppModule

@Single
internal class JsonProvider {
    val json: Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
        explicitNulls = false
    }
}

@Single
internal class HttpClientProvider(jsonProvider: JsonProvider) {
    val httpClient: HttpClient = HttpClient {
        val json = jsonProvider.json
        install(ContentNegotiation) { json(json) }
        install(HttpCache)
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    co.touchlab.kermit.Logger.d("httpClient") { message }
                }
            }
            level = if (DEBUG) LogLevel.ALL else LogLevel.NONE
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 50000
            socketTimeoutMillis = 50000
        }
    }
}

@Single
internal class SettingsProvider {
    val settings: Settings = Settings()
}


