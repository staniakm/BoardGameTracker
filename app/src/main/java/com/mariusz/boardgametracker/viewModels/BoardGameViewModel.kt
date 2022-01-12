package com.mariusz.boardgametracker.viewModels

import androidx.lifecycle.ViewModel
import com.mariusz.boardgametracker.database.InMemoryEventGameTable
import com.mariusz.boardgametracker.database.InMemoryGamesTable
import com.mariusz.boardgametracker.domain.BoardGame
import com.mariusz.boardgametracker.domain.BoardGameStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BoardGameViewModel @Inject constructor(
    private val gamesTable: InMemoryGamesTable,
    private val eventGameTable: InMemoryEventGameTable
) : ViewModel() {

    fun getAllGames() = gamesTable.getGames()
    fun addGame(name: String, gameStatus: BoardGameStatus) =
        gamesTable.addGame(BoardGame(gamesTable.getId(), name, gameStatus))

    fun getEventGames(eventId: Int): List<BoardGame> {
        return eventGameTable.getAllGames(eventId).let { list ->
            gamesTable.getGamesById(list.map { it.gameId })
        }
    }
}