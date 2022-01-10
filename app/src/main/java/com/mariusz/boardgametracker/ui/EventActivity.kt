package com.mariusz.boardgametracker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mariusz.boardgametracker.databinding.ActivityEventBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}