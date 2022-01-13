package com.mariusz.boardgametracker.domain

data class EventAttendee(val id: Int, val name: String) {
    override fun toString(): String {
        return name
    }
}