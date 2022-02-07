package com.mariusz.boardgametracker.viewModels

import androidx.lifecycle.*
import com.mariusz.boardgametracker.domain.BoardGame
import com.mariusz.boardgametracker.domain.GameSession
import com.mariusz.boardgametracker.domain.SessionStatus
import com.mariusz.boardgametracker.repository.BoardGameRepository
import com.mariusz.boardgametracker.repository.GameSessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameSessionViewModel @Inject constructor(
    private val gameSessionRepository: GameSessionRepository,
    private val gameRepository: BoardGameRepository
) : ViewModel() {
    fun getAttendeeScoring(eventId: Int, gameId: Int) {

    }

    fun getRunningGameSession(eventId: Int, gameId: Int): LiveData<GameSession?> {
        return gameSessionRepository.getActiveGameSession(eventId, gameId).asLiveData()
    }

    fun createGameSession(eventId: Int, gameId: Int) = viewModelScope.launch {
        gameSessionRepository.createSession(eventId, gameId, SessionStatus.OPENED)
    }

    fun getSessions(eventId: Int): LiveData<List<BoardGame>> {
        val result = MutableLiveData<List<BoardGame>>()
        viewModelScope.launch {
            gameSessionRepository.getAllSessions(eventId).collect { sessions ->
                gameRepository.getGamesById(sessions.map { it.gameId }).let { gamesFlow ->
                    gamesFlow.collect {
                        result.postValue(it)
                    }
                }
            }
        }
        return result
    }
}