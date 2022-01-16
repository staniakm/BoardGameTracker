package com.mariusz.boardgametracker.viewModels

import androidx.lifecycle.*
import com.mariusz.boardgametracker.domain.BoardGame
import com.mariusz.boardgametracker.domain.BoardGameStatus
import com.mariusz.boardgametracker.repository.BoardGameRepository
import com.mariusz.boardgametracker.repository.EventGameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardGameViewModel @Inject constructor(
    private val boardGameRepository: BoardGameRepository,
    private val eventGameRepository: EventGameRepository
) : ViewModel() {

    fun getAllGames() = boardGameRepository.getGames().asLiveData()
    fun addGame(name: String, gameStatus: BoardGameStatus): MutableLiveData<BoardGame> {
        val result = MutableLiveData<BoardGame>()
        viewModelScope.launch {
            val boardGame = BoardGame(name, gameStatus)
            val returnedrepo = boardGameRepository.addGame(boardGame).let {
                boardGame.copy(id = it.toInt())
            }
            result.postValue(returnedrepo)
        }
        return result
    }

    fun getEventGames(eventId: Int): LiveData<List<BoardGame>> {
        val result = MutableLiveData<List<BoardGame>>()
        viewModelScope.launch {
            eventGameRepository.getAllGamesByEvent(eventId).collect { games ->
                boardGameRepository.getGamesById(games.map { it.gameId }).let { gamesFlow ->
                    gamesFlow.collect {
                        result.postValue(it)
                    }
                }
            }
        }
        return result
    }
}