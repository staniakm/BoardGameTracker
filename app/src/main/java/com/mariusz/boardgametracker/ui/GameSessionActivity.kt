package com.mariusz.boardgametracker.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.mariusz.boardgametracker.adapter.UserScoreAdapter
import com.mariusz.boardgametracker.databinding.ActivityGameSessionBinding
import com.mariusz.boardgametracker.domain.BoardGame
import com.mariusz.boardgametracker.domain.Event
import com.mariusz.boardgametracker.viewModels.GameSessionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameSessionActivity : AppCompatActivity() {

    private val TAG: String = "GameSessionActivity"
    private lateinit var binding: ActivityGameSessionBinding
    private lateinit var adapter: UserScoreAdapter
    private val gameSessionViewModel: GameSessionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameSessionBinding.inflate(layoutInflater)
        adapter = UserScoreAdapter()
        binding.userScore.layoutManager = LinearLayoutManager(this)
        binding.userScore.adapter = adapter

        setContentView(binding.root)


        intent.extras?.let { extras ->
            val boardGame = extras.getSerializable("game") as BoardGame
            val event = extras.getSerializable("event") as Event
            binding.gameName.text = boardGame.name
            gameSessionViewModel.getRunningGameSession(event.id!!, boardGame.id!!)
                .observe(this) {session->
                    println("session? $session")
                    session?.let {
                        println("Already existing session: $it")
                    } ?: gameSessionViewModel.createGameSession(event.id, boardGame.id)
                        .observe(this) {
                            println("Newly created session: $it")
                        }
                }
        }

    }
}