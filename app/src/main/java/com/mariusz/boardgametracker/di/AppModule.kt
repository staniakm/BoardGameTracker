package com.mariusz.boardgametracker.di

import com.mariusz.boardgametracker.ui.InMemoryEventTable
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
}