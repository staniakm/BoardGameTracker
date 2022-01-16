package com.mariusz.boardgametracker.repository

import androidx.lifecycle.LiveData
import com.mariusz.boardgametracker.domain.Attendee
import com.mariusz.boardgametracker.domain.AttendeeDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AttendeeRepository @Inject constructor(private val attendeeDao: AttendeeDao) {
    fun getAttendees(): Flow<List<Attendee>> {
        return attendeeDao.getAllAttendees()
    }

    suspend fun addAttendee(attendee: Attendee): Long {
        return attendeeDao.createAttendee(attendee)
    }

    fun getSelectedAttendees(attendeeIds: List<Int>): Flow<List<Attendee>> {
        return attendeeDao.getSelectedAttendees(attendeeIds)
    }

}