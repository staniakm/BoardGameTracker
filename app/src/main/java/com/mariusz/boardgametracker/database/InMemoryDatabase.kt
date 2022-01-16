package com.mariusz.boardgametracker.database

import com.mariusz.boardgametracker.domain.EventAttendee

object InMemoryEventAttendeeTable {
    private val database: MutableList<EventAttendee> = mutableListOf()

    fun getAllAttendeeIdForEvent(eventId: Int): List<EventAttendee> {
        return database.filter { it.eventId == eventId }
    }

    fun addEventAttendee(attendee: EventAttendee) {
        database.add(attendee)
    }
}

