package com.mariusz.boardgametracker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mariusz.boardgametracker.adapter.UserScoreAdapter
import com.mariusz.boardgametracker.databinding.ActivityGameSessionBinding
import com.mariusz.boardgametracker.domain.Attendee
import com.mariusz.boardgametracker.domain.BoardGame
import com.mariusz.boardgametracker.domain.Event

class GameSessionActivity : AppCompatActivity() {

    private val TAG: String = "GameSessionActivity"
    private lateinit var binding: ActivityGameSessionBinding
    private lateinit var adapter: UserScoreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameSessionBinding.inflate(layoutInflater)
        adapter = UserScoreAdapter()
        binding.userScore.layoutManager = LinearLayoutManager(this)
        binding.userScore.adapter = adapter

        setContentView(binding.root)

        adapter.submitList(listOf(Attendee("test",1)))

        intent.extras?.let { extras ->
            val boardGame = extras.getSerializable("game") as BoardGame
            val event = extras.getSerializable("event") as Event
            binding.gameName.text = boardGame.name
        }

    }
}