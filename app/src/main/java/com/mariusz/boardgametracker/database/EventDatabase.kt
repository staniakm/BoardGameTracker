package com.mariusz.boardgametracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mariusz.boardgametracker.converters.LocalDateConverter
import com.mariusz.boardgametracker.domain.*

@Database(
    entities = [Event::class, EventGame::class, Attendee::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(LocalDateConverter::class)
abstract class EventDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao
    abstract fun eventGameDao(): EventGameDao
    abstract fun attendeeDao(): AttendeeDao
}