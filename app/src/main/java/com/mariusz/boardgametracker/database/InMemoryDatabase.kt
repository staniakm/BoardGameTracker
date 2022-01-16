package com.mariusz.boardgametracker.database

import com.mariusz.boardgametracker.domain.*
import java.time.LocalDate
import java.util.concurrent.atomic.AtomicInteger

object InMemoryEventTable {
    private val idCounter: AtomicInteger = AtomicInteger(1)
    private val database: MutableMap<Int, Event> = mutableMapOf(
        Pair(1, Event(1, "Event", date = LocalDate.now()))
    )

    fun getId() = idCounter.incrementAndGet()
    fun addEvent(event: Event) = database.put(event.id!!, event)
    fun getEvents(): List<Event> {
        return database.map { it.value }
    }

    fun changeStatus(eventId: Int, status: EventStatus): Event {
        return database[eventId]!!.copy(eventStatus = status).let {
            database[eventId] = it
            it
        }

    }
}

object InMemoryEventGameTable {
    private val idCounter: AtomicInteger = AtomicInteger(1)
    private val database: MutableMap<Int, EventGame> = mutableMapOf()

    fun getId() = idCounter.incrementAndGet()
    fun addGameEvent(event: EventGame) {
        database[event.id!!] = event
    }

    fun getAllGames(eventId: Int): List<EventGame> {
        return database.values.filter { it.eventId == eventId }
    }
}

object InMemoryEventAttendeeTable {
    private val database: MutableList<EventAttendee> = mutableListOf()

    fun getAllAttendeeIdForEvent(eventId: Int): List<EventAttendee> {
        return database.filter { it.eventId == eventId }
    }

    fun addEventAttendee(attendee: EventAttendee) {
        database.add(attendee)
    }
}

object InMemoryGamesTable {
    private val idCounter: AtomicInteger = AtomicInteger(1)
    private val database: MutableMap<Int, BoardGame> = mutableMapOf(
        Pair(1, BoardGame(1, "Century"))
    )

    fun getId() = idCounter.incrementAndGet()
    fun addGame(game: BoardGame): BoardGame {
        return database.values.firstOrNull { it.name.uppercase() == game.name.uppercase() }
            ?: database.getOrPut(game.id) { game }
                .let {
                    println(it)
                    it
                }
    }

    fun getGames(): List<BoardGame> {
        return database.map { it.value }
    }

    fun getGamesById(keys: List<Int>): List<BoardGame> {
        return database.filterKeys { keys.contains(it) }.values.toList()
    }
}
