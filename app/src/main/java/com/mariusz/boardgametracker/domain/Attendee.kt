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

data class EventAttendee(val eventId: Int, val attendeeId: Int)

