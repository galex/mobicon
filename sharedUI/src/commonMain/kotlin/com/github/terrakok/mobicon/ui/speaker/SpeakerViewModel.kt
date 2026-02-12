package com.github.terrakok.mobicon.ui.speaker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.mobicon.DataService
import com.github.terrakok.mobicon.Session
import com.github.terrakok.mobicon.Speaker
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
internal class SpeakerViewModel(
    val eventId: String,
    val speakerId: String,
    private val dataService: DataService
) : ViewModel() {

    var speaker by mutableStateOf<Speaker?>(null)
        private set

    private val sessiosState = mutableStateListOf<Session>()
    val sessions: List<Session> = sessiosState

    var loading by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    init {
        loadSpeaker()
    }

    fun loadSpeaker() {
        viewModelScope.launch {
            try {
                loading = true
                error = null
                val s = dataService.getSpeaker(eventId, speakerId)
                speaker = s
                sessiosState.addAll(
                    s.sessions
                        .map { dataService.getSession(eventId, it.toString()) }
                        .sortedBy { it.startsAt }
                )
            } catch (e: Throwable) {
                error = e.message
            } finally {
                loading = false
            }
        }
    }

}
