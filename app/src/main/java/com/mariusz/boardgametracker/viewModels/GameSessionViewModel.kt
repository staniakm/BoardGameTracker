package com.mariusz.boardgametracker.viewModels

import androidx.lifecycle.*
import com.mariusz.boardgametracker.domain.BoardGame
import com.mariusz.boardgametracker.domain.GameSession
import com.mariusz.boardgametracker.domain.SessionAttendeeScoring
import com.mariusz.boardgametracker.domain.SessionStatus
import com.mariusz.boardgametracker.repository.BoardGameRepository
import com.mariusz.boardgametracker.repository.EventAttendeeRepository
import com.mariusz.boardgametracker.repository.GameSessionRepository
import com.mariusz.boardgametracker.repository.ScoringRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameSessionViewModel @Inject constructor(
    private val gameSessionRepository: GameSessionRepository,
    private val gameRepository: BoardGameRepository,
    private val sessionAttendeeScoringRepository: ScoringRepository,
    private val eventAttendeeRepository: EventAttendeeRepository
) : ViewModel() {
    fun getAttendeeScoring(eventId: Int, gameId: Int) {

    }

    fun getRunningGameSession(eventId: Int, gameId: Int): LiveData<GameSession?> {
        return gameSessionRepository.getActiveGameSession(eventId, gameId).asLiveData()
    }

    fun createGameSession(eventId: Int, gameId: Int): LiveData<Long> {
        val result = MutableLiveData<Long>()
        viewModelScope.launch {
            gameSessionRepository.createSession(eventId, gameId, SessionStatus.OPENED)
                .let { sessionId ->
                    eventAttendeeRepository.getAllAttendeeIdForEvent(eventId)
                        .collect { eventAttendees ->
                            eventAttendees.map { att ->
                                SessionAttendeeScoring(sessionId, att.attendeeId, 0)
                            }.let {
                                sessionAttendeeScoringRepository.createAttendees(it)
                            }
                        }
                    gameSessionRepository.getSession(sessionId)
                }
        }
        return result
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