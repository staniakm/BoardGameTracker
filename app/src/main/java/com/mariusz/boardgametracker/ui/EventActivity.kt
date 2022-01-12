package com.mariusz.boardgametracker.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mariusz.boardgametracker.databinding.ActivityEventBinding
import com.mariusz.boardgametracker.domain.Event
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
            binding.eventName.text = event.name
        }

        binding.startEvent.setOnClickListener {
            eventViewModel.startEvent(event.id)
        }

        binding.finishEvent.setOnClickListener {
            eventViewModel.finishEvent(event.id)
        }


    }
}