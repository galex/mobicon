package com.github.terrakok.mobicon.ui.root

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.mobicon.DeeplinkService
import com.github.terrakok.mobicon.SettingsProvider
import com.github.terrakok.mobicon.logger.Logger
import com.russhwolf.settings.Settings
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
internal class RootViewModel(
    private val deeplinkService: DeeplinkService,
    settingsProvider: SettingsProvider,
    private val logger: Logger
) : ViewModel() {
    private val settings: Settings = settingsProvider.settings
    private companion object {
        const val LAST_EVENT_ID_KEY = "lastEventId"
        const val TAG = "RootViewModel"
    }

    var initialStack = mutableStateListOf<AppScreen>()
        private set

    init {
        logger.info(TAG, "RootViewModel initialized")
        viewModelScope.launch {
            deeplinkService.deepLink.collect { url ->
                logger.debug(TAG, "Received deep link: $url")
                initialStack.clear()
                val newStack = urlToStack(url)
                initialStack.addAll(newStack)
                logger.info(TAG, "Navigation stack updated: ${newStack.map { it::class.simpleName }}")
            }
        }
    }

    fun saveSelectedEvent(eventId: String) {
        logger.debug(TAG, "Saving selected event: $eventId")
        settings.putString(LAST_EVENT_ID_KEY, eventId)
        logger.info(TAG, "Event saved to settings: $eventId")
    }

    private fun urlToStack(url: String): List<AppScreen> {
        logger.debug(TAG, "Processing URL: $url")
        val stack = DeeplinkService.urlToStack(url)
        logger.debug(TAG, "Parsed stack: ${stack.map { it::class.simpleName }}")
        
        if (stack.size == 1 && stack.single() is EventsListScreen) {
            val lastEventId = settings.getStringOrNull(LAST_EVENT_ID_KEY)
            return if (lastEventId != null) {
                logger.info(TAG, "Restoring last event: $lastEventId")
                listOf(EventScreen(lastEventId))
            } else {
                logger.debug(TAG, "No last event found, showing events list")
                stack
            }
        } else {
            val eventScreen = stack.firstOrNull { it is EventScreen } as EventScreen?
            if (eventScreen != null) {
                logger.info(TAG, "Navigating to event from deep link: ${eventScreen.id}")
                saveSelectedEvent(eventScreen.id)
            }
            return stack
        }
    }
}
