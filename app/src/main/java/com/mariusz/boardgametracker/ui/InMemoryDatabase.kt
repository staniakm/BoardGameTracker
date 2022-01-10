package com.mariusz.boardgametracker.ui

import com.mariusz.boardgametracker.domain.Event
import java.util.concurrent.atomic.AtomicInteger

object InMemoryEventTable {
    private val idCounter: AtomicInteger = AtomicInteger(0)
    private val database: MutableMap<Int, Event> = mutableMapOf()

    fun getId() = idCounter.incrementAndGet()
    fun addEvent(event: Event) = database.put(event.id, event)
    fun getEvents(): List<Event> {
        return database.map { it.value }
    }


}