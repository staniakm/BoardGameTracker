package com.mariusz.boardgametracker.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mariusz.boardgametracker.adapter.GamesAdapter
import com.mariusz.boardgametracker.databinding.ActivityGameListBinding
import com.mariusz.boardgametracker.domain.BoardGame
import com.mariusz.boardgametracker.viewModels.BoardGameView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameListActivity : AppCompatActivity() {

    private val TAG = "GameListActivity"

    private val gamesViewModel: BoardGameView by viewModels()
    private lateinit var binding: ActivityGameListBinding
    private lateinit var gamesAdapter: GamesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gamesAdapter = GamesAdapter(onGameClick)
        binding.rvGames.layoutManager = LinearLayoutManager(this)
        binding.rvGames.adapter = gamesAdapter

        loadData()

    }

    private fun loadData() {
        gamesViewModel.getAllGames().let {
            gamesAdapter.submitList(it)
        }
    }

    private val onGameClick: (BoardGame) -> Unit = { game ->
        Log.i(TAG, "${game.name}: ")
    }
}