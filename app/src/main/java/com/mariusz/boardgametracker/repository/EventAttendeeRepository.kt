package com.mariusz.boardgametracker.repository

import com.mariusz.boardgametracker.domain.EventAttendee
import com.mariusz.boardgametracker.domain.EventAttendeeDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EventAttendeeRepository @Inject constructor(private val eventAttendeeDao: EventAttendeeDao) {
    suspend fun addEventAttendee(eventAttendee: EventAttendee) {
        eventAttendeeDao.addEventAttendee(eventAttendee)
    }

    fun getAllAttendeeIdForEvent(eventId: Int): Flow<List<EventAttendee>> {
        return eventAttendeeDao.getAllEventAttendeeIds(eventId)
    }
}