package com.mariusz.boardgametracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mariusz.boardgametracker.converters.LocalDateConverter
import com.mariusz.boardgametracker.domain.Event
import com.mariusz.boardgametracker.domain.EventDao
import com.mariusz.boardgametracker.domain.EventGame
import com.mariusz.boardgametracker.domain.EventGameDao

@Database(entities = [Event::class, EventGame::class], version = 1, exportSchema = false)
@TypeConverters(LocalDateConverter::class)
abstract class EventDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao
    abstract fun eventGameDao(): EventGameDao
}