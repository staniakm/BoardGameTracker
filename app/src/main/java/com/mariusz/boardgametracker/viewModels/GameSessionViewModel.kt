package com.mariusz.boardgametracker.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mariusz.boardgametracker.domain.GameSession
import com.mariusz.boardgametracker.domain.SessionStatus
import com.mariusz.boardgametracker.repository.GameSessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameSessionViewModel @Inject constructor(
    private val gameSessionRepository: GameSessionRepository,
) : ViewModel() {
    fun getAttendeeScoring(eventId: Int, gameId: Int) {

    }

    fun getGameSession(eventId: Int, gameId: Int): LiveData<GameSession?> {
        return gameSessionRepository.getActiveGameSession(eventId, gameId).asLiveData()
    }

    fun createGameSession(eventId: Int, gameId: Int) = viewModelScope.launch {
        gameSessionRepository.createSession(eventId, gameId, SessionStatus.OPENED)
    }
}