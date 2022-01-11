package com.mariusz.boardgametracker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mariusz.boardgametracker.databinding.ActivityEventBinding
import com.mariusz.boardgametracker.domain.Event
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let { extras ->
            val event = extras.getSerializable("event") as Event
            binding.eventName.text = event.name
        }

    }
}