package com.mariusz.boardgametracker.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mariusz.boardgametracker.database.InMemoryEventAttendeeTable
import com.mariusz.boardgametracker.database.InMemoryEventGameTable
import com.mariusz.boardgametracker.domain.*
import com.mariusz.boardgametracker.repository.EventGameRepository
import com.mariusz.boardgametracker.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val eventGameRepository: EventGameRepository,
    private val eventAttendeeTable: InMemoryEventAttendeeTable
) : ViewModel() {


    fun storeEvent(name: String, eventDate: LocalDate) {
        return getEventStatus(eventDate).let { status ->
            Event(name = name, date = eventDate, eventStatus = status).let {
                viewModelScope.launch {
                    eventRepository.addEvent(it)
                }
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

    fun getEvents(): LiveData<List<Event>> {
        return eventRepository.getAllEvents().asLiveData()
    }

    fun startEvent(eventId: Int) = viewModelScope.launch {
        eventRepository.updateStatus(eventId, EventStatus.OPEN)
    }

    fun finishEvent(eventId: Int) = viewModelScope.launch {
        eventRepository.updateStatus(eventId, EventStatus.CLOSED)
    }

    fun addEventGame(eventId: Int, boardgameInt: Int) = viewModelScope.launch {
        eventGameRepository.addGameEvent(EventGame(eventId, boardgameInt))
    }

    fun addEventAttendee(id: Int, selectedAttendee: Attendee) {
        eventAttendeeTable.addEventAttendee(EventAttendee(id, selectedAttendee.id))
    }

    fun getAllAttendeesIds(eventId: Int): List<Int> {
        return eventAttendeeTable.getAllAttendeeIdForEvent(eventId).map { it.attendeeId }
    }
}