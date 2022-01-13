package com.mariusz.boardgametracker.domain

data class Attendee(val id: Int, val name: String) {
    override fun toString(): String {
        return name
    }
}

data class EventAttendee(val eventId: Int, val attendeeId: Int)

