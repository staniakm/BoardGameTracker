package com.mariusz.boardgametracker.repository

import com.mariusz.boardgametracker.domain.EventGame
import com.mariusz.boardgametracker.domain.EventGameDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EventGameRepository @Inject constructor(private val eventGameDao: EventGameDao) {
    suspend fun addGameEvent(eventGame: EventGame) {
        eventGameDao.createEventGame(eventGame)
    }

    fun getAllGamesByEvent(eventId: Int): Flow<List<EventGame>> {
        return eventGameDao.getAllEventGames(eventId)
    }
}