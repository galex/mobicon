package com.github.terrakok.mobicon.ui.root

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.mobicon.DeeplinkService
import com.github.terrakok.mobicon.SettingsProvider
import com.russhwolf.settings.Settings
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
internal class RootViewModel(
    private val deeplinkService: DeeplinkService,
    settingsProvider: SettingsProvider
) : ViewModel() {
    private val settings: Settings = settingsProvider.settings
    private companion object {
        const val LAST_EVENT_ID_KEY = "lastEventId"
    }

    var initialStack = mutableStateListOf<AppScreen>()
        private set

    init {
        viewModelScope.launch {
            deeplinkService.deepLink.collect { url ->
                initialStack.clear()
                initialStack.addAll(urlToStack(url))
            }
        }
    }

    fun saveSelectedEvent(eventId: String) {
        settings.putString(LAST_EVENT_ID_KEY, eventId)
    }

    private fun urlToStack(url: String): List<AppScreen> {
        val stack = DeeplinkService.urlToStack(url)
        if (stack.size == 1 && stack.single() is EventsListScreen) {
            val lastEventId = settings.getStringOrNull(LAST_EVENT_ID_KEY)
            return if (lastEventId != null) listOf(EventScreen(lastEventId)) else stack
        } else {
            val eventScreen = stack.firstOrNull { it is EventScreen } as EventScreen?
            if (eventScreen != null) {
                saveSelectedEvent(eventScreen.id)
            }
            return stack
        }
    }
}
