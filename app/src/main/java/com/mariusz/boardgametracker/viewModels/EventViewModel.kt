package com.mariusz.boardgametracker.viewModels

import androidx.lifecycle.ViewModel
import com.mariusz.boardgametracker.database.InMemoryEventAttendeeTable
import com.mariusz.boardgametracker.database.InMemoryEventGameTable
import com.mariusz.boardgametracker.database.InMemoryEventTable
import com.mariusz.boardgametracker.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventTable: InMemoryEventTable,
    private val eventGameTable: InMemoryEventGameTable,
    private val eventAttendeeTable: InMemoryEventAttendeeTable
) : ViewModel() {
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

    fun startEvent(eventId: Int) = eventTable.changeStatus(eventId, EventStatus.OPEN)

    fun finishEvent(eventId: Int) = eventTable.changeStatus(eventId, EventStatus.CLOSED)

    fun addEventGame(eventId: Int, boardgameInt: Int) {
        eventGameTable.addGameEvent(EventGame(eventGameTable.getId(), eventId, boardgameInt))
    }

    fun addEventAttendee(id: Int, selectedAttendee: Attendee) {
        eventAttendeeTable.addEventAttendee(EventAttendee(id, selectedAttendee.id))
    }

    fun getAllAttendeesIds(eventId: Int): List<Int> {
        return eventAttendeeTable.getAllAttendeeIdForEvent(eventId).map { it.attendeeId }
    }
}