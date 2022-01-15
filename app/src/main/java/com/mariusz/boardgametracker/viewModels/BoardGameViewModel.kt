package com.mariusz.boardgametracker.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.mariusz.boardgametracker.database.InMemoryEventGameTable
import com.mariusz.boardgametracker.database.InMemoryGamesTable
import com.mariusz.boardgametracker.domain.BoardGame
import com.mariusz.boardgametracker.domain.BoardGameStatus
import com.mariusz.boardgametracker.repository.EventGameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class BoardGameViewModel @Inject constructor(
    private val gamesTable: InMemoryGamesTable,
    private val eventGameRepository: EventGameRepository
) : ViewModel() {

    fun getAllGames() = gamesTable.getGames()
    fun addGame(name: String, gameStatus: BoardGameStatus) =
        gamesTable.addGame(BoardGame(gamesTable.getId(), name, gameStatus))

    fun getEventGames(eventId: Int): LiveData<List<BoardGame>> {
        return eventGameRepository.getAllGamesByEvent(eventId).asLiveData()
            .map {
                gamesTable.getGamesById(it.map { it.gameId })
            }
    }
}