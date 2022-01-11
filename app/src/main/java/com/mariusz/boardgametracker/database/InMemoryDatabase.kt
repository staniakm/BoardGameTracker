package com.mariusz.boardgametracker.database

import com.mariusz.boardgametracker.domain.BoardGame
import com.mariusz.boardgametracker.domain.Event
import java.time.LocalDate
import java.util.concurrent.atomic.AtomicInteger

object InMemoryEventTable {
    private val idCounter: AtomicInteger = AtomicInteger(0)
    private val database: MutableMap<Int, Event> = mutableMapOf(
        Pair(1, Event(1, "Event", date = LocalDate.now()))
    )

    fun getId() = idCounter.incrementAndGet()
    fun addEvent(event: Event) = database.put(event.id, event)
    fun getEvents(): List<Event> {
        return database.map { it.value }
    }
}

object InMemoryGamesTable {
    private val idCounter: AtomicInteger = AtomicInteger(0)
    private val database: MutableMap<Int, BoardGame> = mutableMapOf(
        Pair(1, BoardGame(1, "Century"))
    )

    fun getId() = idCounter.incrementAndGet()
    fun addGame(game: BoardGame) = database.put(game.id, game)
    fun getGames(): List<BoardGame> {
        return database.map { it.value }
    }
}