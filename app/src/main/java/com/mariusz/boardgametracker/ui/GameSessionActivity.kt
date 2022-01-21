package com.mariusz.boardgametracker.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mariusz.boardgametracker.databinding.ActivityGameSessionBinding
import com.mariusz.boardgametracker.domain.BoardGame
import com.mariusz.boardgametracker.domain.Event

class GameSessionActivity : AppCompatActivity() {

    private val TAG: String = "GameSessionActivity"
    private lateinit var binding: ActivityGameSessionBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameSessionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let { extras ->
            val boardGame = extras.getSerializable("game") as BoardGame
            val event = extras.getSerializable("event") as Event
            binding.gameName.text = boardGame.name
        }

    }
}