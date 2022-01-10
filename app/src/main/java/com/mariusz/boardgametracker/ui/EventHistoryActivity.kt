package com.mariusz.boardgametracker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mariusz.boardgametracker.databinding.ActivityEventHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}