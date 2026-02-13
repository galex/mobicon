package com.github.terrakok.mobicon

import com.github.terrakok.mobicon.logger.Logger
import org.koin.core.KoinApplication
import org.koin.dsl.KoinAppDeclaration
import org.koin.plugin.module.dsl.startKoin

/**
 * Initialize Koin for the application.
 * Call this from platform-specific entry points (Android Application, Desktop main, etc.)
 * 
 * @param config Optional additional Koin configuration
 */
fun initKoin(config: KoinAppDeclaration? = null): KoinApplication {
    return startKoin<KoinApp> {
        config?.invoke(this)
        koin.get<Logger>().debug("Initialization", "Koin initialized")
    }
}