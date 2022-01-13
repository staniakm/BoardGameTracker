package com.mariusz.boardgametracker.di

import com.mariusz.boardgametracker.database.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideAttendeeTable() = InMemoryAttendeeTable

    @Singleton
    @Provides
    fun provideEventAttendeeTable() = InMemoryEventAttendeeTable
}