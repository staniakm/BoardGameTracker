package com.mariusz.boardgametracker.viewModels

import androidx.lifecycle.ViewModel
import com.mariusz.boardgametracker.database.InMemoryGamesTable
import com.mariusz.boardgametracker.domain.BoardGame
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BoardGameView @Inject constructor(private val gamesTable: InMemoryGamesTable) : ViewModel() {

    fun getAllGames() = gamesTable.getGames()
    fun addGame(name: String) = gamesTable.addGame(BoardGame(gamesTable.getId(), name))
}