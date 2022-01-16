package com.mariusz.boardgametracker.viewModels

import androidx.lifecycle.*
import com.mariusz.boardgametracker.domain.Attendee
import com.mariusz.boardgametracker.repository.AttendeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttendeeGameViewModel @Inject constructor(private val attendeeRepository: AttendeeRepository) :
    ViewModel() {
    fun getAllAttendee(): LiveData<List<Attendee>> {
        return attendeeRepository.getAttendees().asLiveData()
    }

    fun createNewAttendee(selectedAttendee: Attendee): LiveData<Long> {
        val result = MutableLiveData<Long>()
        viewModelScope.launch {
            val returnedrepo = attendeeRepository.addAttendee(selectedAttendee)
            result.postValue(returnedrepo)
        }
        return result
    }

    fun getAttendees(attendeeIds: List<Int>): LiveData<List<Attendee>> {
        return attendeeRepository.getSelectedAttendees(attendeeIds).asLiveData()
    }


}
