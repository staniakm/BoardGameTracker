package com.mariusz.boardgametracker.di

import com.mariusz.boardgametracker.database.InMemoryEventGameTable
import com.mariusz.boardgametracker.database.InMemoryEventTable
import com.mariusz.boardgametracker.database.InMemoryGamesTable
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
}