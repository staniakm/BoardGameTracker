package com.mariusz.boardgametracker.domain

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity
data class Attendee(
    val name: String,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
) {
    override fun toString(): String {
        return name
    }
}

@Dao
interface AttendeeDao {
    @Query("select * from attendee")
    fun getAllAttendees(): Flow<List<Attendee>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createAttendee(attendee: Attendee): Long

    @Query("select * from attendee where id in (:attendeeIds)")
    fun getSelectedAttendees(attendeeIds: List<Int>): Flow<List<Attendee>>

}

@Entity
data class EventAttendee(
    val eventId: Int,
    val attendeeId: Int,
    @PrimaryKey(autoGenerate = true) val id: Long? = null
)

@Dao
interface EventAttendeeDao {
    @Insert
    suspend fun addEventAttendee(eventAttendee: EventAttendee)

    @Query("select * from EventAttendee where eventId = :eventId")
    fun getAllEventAttendeeIds(eventId: Int): Flow<List<EventAttendee>>

}

@Entity
data class SessionAttendeeScoring(
    val sessionId: Long,
    val attendeeId: Int,
    val score: Int,
    @PrimaryKey(autoGenerate = true) val id: Long? = null
)

@Dao
interface SessionAttendeeScoringDao {
    @Insert
    suspend fun addEventAttendee(eventAttendees: List<SessionAttendeeScoring>)
}