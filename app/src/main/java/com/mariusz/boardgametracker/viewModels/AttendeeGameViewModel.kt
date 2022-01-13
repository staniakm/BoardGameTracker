package com.mariusz.boardgametracker.viewModels

import androidx.lifecycle.ViewModel
import com.mariusz.boardgametracker.database.InMemoryAttendeeTable
import com.mariusz.boardgametracker.domain.Attendee
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AttendeeGameViewModel @Inject constructor(private val attendeeTable: InMemoryAttendeeTable) :
    ViewModel() {
    fun getAllAttendee(): List<Attendee> {
        return attendeeTable.getAttendees()
    }

    fun createNewAttendee(selectedAttendee: Attendee): Attendee {
        return attendeeTable.addAttendee(selectedAttendee.copy(id = attendeeTable.getId()))
    }

    fun getAttendees(attendeeIds: List<Int>): List<Attendee> {
        return attendeeTable.getSelectedAttendees(attendeeIds)
    }


}
