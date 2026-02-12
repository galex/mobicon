package com.github.terrakok.mobicon.ui.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.mobicon.DataService
import com.github.terrakok.mobicon.EventInfo
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
internal class EventInfoViewModel(
    val eventId: String,
    private val dataService: DataService
) : ViewModel() {

    var eventInfo by mutableStateOf<EventInfo?>(null)
        private set

    var loading by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            try {
                loading = true
                error = null
                eventInfo = dataService.getEventInfo(eventId)
            } catch (e: Throwable) {
                error = e.message
            } finally {
                loading = false
            }
        }
    }
}
