package com.mariusz.boardgametracker.repository

import com.mariusz.boardgametracker.domain.GameSession
import com.mariusz.boardgametracker.domain.GameSessionDao
import com.mariusz.boardgametracker.domain.SessionStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameSessionRepository @Inject constructor(private val gamesSessionDao: GameSessionDao) {


    fun getActiveGameSession(eventId: Int, gameId: Int): Flow<GameSession?> {
        return gamesSessionDao.getGameActiveSession(eventId, gameId, SessionStatus.OPENED)
    }

    suspend fun createSession(eventId: Int, gameId: Int, status: SessionStatus): Long {
        return GameSession(eventId, gameId, SessionStatus.OPENED).let {
            gamesSessionDao.createGameSession(it)
        }
    }

    fun getAllSessions(eventId: Int): Flow<List<GameSession>> {
        return gamesSessionDao.getAllEventSessions(eventId)
    }


}
