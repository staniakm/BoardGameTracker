package com.mariusz.boardgametracker.di

import android.content.Context
import androidx.room.Room
import com.mariusz.boardgametracker.database.*
import com.mariusz.boardgametracker.domain.AttendeeDao
import com.mariusz.boardgametracker.domain.EventDao
import com.mariusz.boardgametracker.domain.EventGameDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideEventTable() = InMemoryEventTable

    @Singleton
    @Provides
    fun provideGamesTable() = InMemoryGamesTable

    @Singleton
    @Provides
    fun provideEventGameTable() = InMemoryEventGameTable

    @Singleton
    @Provides
    fun provideEventAttendeeTable() = InMemoryEventAttendeeTable
}

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext applicationContext: Context): EventDatabase {
        return Room.databaseBuilder(
            applicationContext,
            EventDatabase::class.java,
            "event-database"
        ).build()
    }


    @Provides
    fun provideEventDao(appDatabase: EventDatabase): EventDao {
        return appDatabase.eventDao()
    }

    @Provides
    fun provideEventGameDao(appDatabase: EventDatabase): EventGameDao {
        return appDatabase.eventGameDao()
    }

    @Provides
    fun provideAttendeeDao(appDatabase: EventDatabase): AttendeeDao {
        return appDatabase.attendeeDao()
    }
}