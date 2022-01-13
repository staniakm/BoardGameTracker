package com.mariusz.boardgametracker.viewModels

import androidx.lifecycle.ViewModel
import com.mariusz.boardgametracker.domain.EventAttendee
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AttendeeGameViewModel @Inject constructor() : ViewModel() {
    fun getAllAttendee(): List<EventAttendee> {
        return listOf(EventAttendee(1, "Mario"), EventAttendee(2, "Joanna"))
    }


}
