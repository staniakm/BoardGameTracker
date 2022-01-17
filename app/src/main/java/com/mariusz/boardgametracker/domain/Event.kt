package com.mariusz.boardgametracker.domain

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import com.mariusz.boardgametracker.R
import kotlinx.coroutines.flow.Flow
import java.io.Serializable
import java.time.LocalDate

@Entity
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val date: LocalDate = LocalDate.now(),
    val eventStatus: EventStatus = EventStatus.OPEN
) : Serializable

@Dao
interface EventDao {
    @Insert(onConflict = IGNORE)
    suspend fun storeEvent(event: Event)

    @Query("select * from event order by date asc")
    fun getAllEvents(): Flow<List<Event>>

    @Query("update event set eventStatus = :status where id = :eventId")
    suspend fun updateStatus(eventId: Int, status: EventStatus)

    @Query("delete from Event where id = :eventId")
    suspend fun deleteEvent(eventId: Int)
}


enum class EventStatus(val iconId: Int) {
    SCHEDULED(R.drawable.event_scheduled), OPEN(R.drawable.event_open), CLOSED(R.drawable.event_finished)
}

@Entity
data class EventGame(
    val eventId: Int,
    val gameId: Int,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)

@Dao
interface EventGameDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createEventGame(eventGame: EventGame)

    @Query("select * from eventgame where eventId = :eventId")
    fun getAllEventGames(eventId: Int): Flow<List<EventGame>>

}