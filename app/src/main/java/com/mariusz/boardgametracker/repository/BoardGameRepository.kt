package com.mariusz.boardgametracker.repository

import com.mariusz.boardgametracker.domain.BoardGame
import com.mariusz.boardgametracker.domain.BoardGameDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BoardGameRepository @Inject constructor(private val boardGameDao: BoardGameDao) {
    fun getGames(): Flow<List<BoardGame>> {
        return boardGameDao.getAllGames()
    }

    suspend fun addGame(boardGame: BoardGame): Long {
        return boardGameDao.addGame(boardGame)
    }

    fun getGamesById(gameIds: List<Int>): Flow<List<BoardGame>> {
        return boardGameDao.getSelectedGames(gameIds)
    }

}