package com.mariusz.boardgametracker.domain

import androidx.room.*
import com.mariusz.boardgametracker.R
import kotlinx.coroutines.flow.Flow
import java.io.Serializable

@Entity
data class BoardGame(
    val name: String,
    val gameStatus: BoardGameStatus = BoardGameStatus.HOME,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) : Serializable {
    override fun toString(): String {
        return name
    }
}

enum class BoardGameStatus(val iconId: Int) {
    HOME(R.drawable.game_status_home), BORROW(R.drawable.game_status_borrow)
}


@Dao
interface BoardGameDao {
    @Query("select * from BoardGame")
    fun getAllGames(): Flow<List<BoardGame>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGame(boardGame: BoardGame): Long

    @Query("select * from BoardGame where id in (:gameIds)")
    fun getSelectedGames(gameIds: List<Int>): Flow<List<BoardGame>>

}