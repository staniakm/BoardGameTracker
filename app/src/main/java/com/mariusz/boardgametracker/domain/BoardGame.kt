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

@Entity
data class GameSession(
    val eventId: Int,
    val gameId: Int,
    val sessionStatus: SessionStatus = SessionStatus.OPENED,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)

@Dao
interface GameSessionDao {

    @Query("select * from GameSession where eventId=:eventId and gameId=:gameId and sessionStatus=:sessionStatus")
    fun getGameActiveSession(
        eventId: Int,
        gameId: Int,
        sessionStatus: SessionStatus
    ): Flow<GameSession?>

    @Insert
    suspend fun createGameSession(gameSession: GameSession)

    @Query("select * from GameSession where eventId=:eventId")
    fun getAllEventSessions(eventId: Int):Flow<List<GameSession>>

}

enum class SessionStatus {
    OPENED, CLOSED
}