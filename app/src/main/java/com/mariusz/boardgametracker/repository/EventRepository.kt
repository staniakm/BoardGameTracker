package com.mariusz.boardgametracker.repository

import com.mariusz.boardgametracker.domain.Event
import com.mariusz.boardgametracker.domain.EventDao
import com.mariusz.boardgametracker.domain.EventStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EventRepository @Inject constructor(private val eventDao: EventDao) {
    suspend fun addEvent(event: Event) {
        eventDao.storeEvent(event)
    }

    fun getAllEvents(): Flow<List<Event>> {
        return eventDao.getAllEvents()
    }

    suspend fun updateStatus(eventId: Int, status: EventStatus) {
        eventDao.updateStatus(eventId, status)
    }


}