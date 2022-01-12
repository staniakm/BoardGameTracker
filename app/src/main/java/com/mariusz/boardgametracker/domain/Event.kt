package com.mariusz.boardgametracker.domain

import com.mariusz.boardgametracker.R
import java.io.Serializable
import java.time.LocalDate

data class Event(
    val id: Int,
    val name: String,
    val date: LocalDate = LocalDate.now(),
    val eventStatus: EventStatus = EventStatus.OPEN
) : Serializable

enum class EventStatus(val iconId: Int) {
    SCHEDULED(R.drawable.event_scheduled), OPEN(R.drawable.event_open), CLOSED(R.drawable.event_finished)
}

data class EventGame(val id: Int, val eventId: Int, val gameId: Int)