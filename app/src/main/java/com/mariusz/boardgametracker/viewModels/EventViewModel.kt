package com.mariusz.boardgametracker.viewModels

import androidx.lifecycle.ViewModel
import com.mariusz.boardgametracker.database.InMemoryEventTable
import com.mariusz.boardgametracker.domain.Event
import com.mariusz.boardgametracker.domain.EventStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(private val eventTable: InMemoryEventTable) : ViewModel() {
    fun storeEvent(event: String, eventDate: LocalDate): Event {
        return getEventStatus(eventDate).let { status ->
            Event(eventTable.getId(), event, eventDate, status).let {
                eventTable.addEvent(it)
                it
            }

        }
    }

    private fun getEventStatus(eventDate: LocalDate): EventStatus {
        val now = LocalDate.now()
        return when {
            now.isBefore(eventDate) -> EventStatus.SCHEDULED
            now.isAfter(eventDate) -> EventStatus.CLOSED
            else -> EventStatus.OPEN
        }
    }

    fun getEvents(): List<Event> {
        return eventTable.getEvents().sortedBy { it.date }
    }

    fun startEvent(eventId: Int) {
        eventTable.changeStatus(eventId, EventStatus.OPEN)
    }

    fun finishEvent(eventId: Int) {
        eventTable.changeStatus(eventId, EventStatus.CLOSED)
    }
}