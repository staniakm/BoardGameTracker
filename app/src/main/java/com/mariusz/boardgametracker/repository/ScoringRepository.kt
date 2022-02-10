package com.mariusz.boardgametracker.repository

import com.mariusz.boardgametracker.domain.SessionAttendeeScoring
import com.mariusz.boardgametracker.domain.SessionAttendeeScoringDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScoringRepository @Inject constructor(private val attendeeScoringDao: SessionAttendeeScoringDao) {

    suspend fun createAttendees(attendees: List<SessionAttendeeScoring>): List<Long> {
        return attendeeScoringDao.addEventAttendee(attendees)
    }

    fun getSessionAttendees(sessionId: Long): Flow<List<SessionAttendeeScoring>> {
        return attendeeScoringDao.getAttendees(sessionId)
    }

}