package com.mariusz.boardgametracker.viewModels

import androidx.lifecycle.ViewModel
import com.mariusz.boardgametracker.domain.GameSession
import com.mariusz.boardgametracker.domain.SessionStatus
import com.mariusz.boardgametracker.repository.GameSessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameSessionViewModel @Inject constructor(
    private val gameSessionRepository: GameSessionRepository,
) : ViewModel() {
    fun getAttendeeScoring(eventId: Int, gameId: Int) {

    }

    fun getGameSession(eventId: Int, gameId: Int): GameSession? {
        return gameSessionRepository.getActiveGameSession(eventId, gameId)
    }

    fun createGameSession(eventId: Int, gameId: Int) {
        gameSessionRepository.createSession(eventId, gameId, SessionStatus.OPENED)
    }
}