package com.mariusz.boardgametracker.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mariusz.boardgametracker.databinding.ActivityEventBinding
import com.mariusz.boardgametracker.domain.Event
import com.mariusz.boardgametracker.domain.EventStatus
import com.mariusz.boardgametracker.viewModels.EventViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventActivity : AppCompatActivity() {

    private val eventViewModel: EventViewModel by viewModels()
    private lateinit var binding: ActivityEventBinding
    private lateinit var event: Event
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let { extras ->
            event = extras.getSerializable("event") as Event
            loadData()
        }

        binding.startEvent.setOnClickListener {
            event = eventViewModel.startEvent(event.id)
            loadData()
        }

        binding.finishEvent.setOnClickListener {
            event = eventViewModel.finishEvent(event.id)
            loadData()
        }
    }

    private fun loadData() {
        binding.eventName.text = event.name
        when (event.eventStatus) {
            EventStatus.SCHEDULED -> binding.finishEvent.visibility = View.GONE
            EventStatus.OPEN -> binding.startEvent.visibility = View.GONE
            EventStatus.CLOSED -> {
                binding.finishEvent.visibility = View.GONE
                binding.startEvent.visibility = View.GONE
            }
        }
    }
}