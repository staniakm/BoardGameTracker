package com.mariusz.boardgametracker.repository

import com.mariusz.boardgametracker.domain.SessionAttendeeScoring
import com.mariusz.boardgametracker.domain.SessionAttendeeScoringDao
import javax.inject.Inject

class ScoringRepository @Inject constructor(private val attendeeScoringDao: SessionAttendeeScoringDao) {

    suspend fun createAttendees(attendees: List<SessionAttendeeScoring>) {
        attendeeScoringDao.addEventAttendee(attendees)
    }

}