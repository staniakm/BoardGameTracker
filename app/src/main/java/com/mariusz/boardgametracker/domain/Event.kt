package com.mariusz.boardgametracker.domain

import java.io.Serializable
import java.time.LocalDate

data class Event(
    val id: Int,
    val name: String,
    val date: LocalDate = LocalDate.now(),
    val eventStatus: EventStatus = EventStatus.OPEN
) : Serializable

enum class EventStatus(val description: String) {
    SCHEDULED("Zaplanowany"), OPEN("W trakcie"), CLOSED("Zakończony")
}