package com.mariusz.boardgametracker.viewModels

import androidx.lifecycle.ViewModel
import com.mariusz.boardgametracker.domain.Event
import com.mariusz.boardgametracker.ui.InMemoryEventTable
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(private val eventTable: InMemoryEventTable) : ViewModel() {
    fun storeEvent(event: String) {
        eventTable.addEvent(Event(eventTable.getId(), event))
    }

    fun getEvents(): List<Event> {
        return eventTable.getEvents()
    }
}