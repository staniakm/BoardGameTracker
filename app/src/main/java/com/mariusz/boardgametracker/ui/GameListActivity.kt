package com.mariusz.boardgametracker.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mariusz.boardgametracker.adapter.GamesAdapter
import com.mariusz.boardgametracker.databinding.ActivityGameListBinding
import com.mariusz.boardgametracker.databinding.AddGameViewBinding
import com.mariusz.boardgametracker.domain.BoardGame
import com.mariusz.boardgametracker.domain.BoardGameStatus
import com.mariusz.boardgametracker.viewModels.BoardGameView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameListActivity : AppCompatActivity() {

    private val TAG = "GameListActivity"

    private val gamesViewModel: BoardGameView by viewModels()
    private lateinit var binding: ActivityGameListBinding
    private lateinit var addGameBinding: AddGameViewBinding
    private lateinit var gamesAdapter: GamesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addGameBinding = AddGameViewBinding.inflate(layoutInflater)

        gamesAdapter = GamesAdapter(onGameClick)
        binding.rvGames.layoutManager = LinearLayoutManager(this)
        binding.rvGames.adapter = gamesAdapter

        binding.fab.setOnClickListener {
            newEventDialog()
        }
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

    @SuppressLint("ResourceType")
    private fun newEventDialog() {
        addGameBinding.root.parent?.let {
            (it as ViewGroup).removeView(addGameBinding.root)
        }
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Create new event")

        builder.setView(addGameBinding.root)
        builder.setPositiveButton("OK") { _, _ ->
            if (addGameBinding.title.text.isNotBlank()) {
                createNewGame(
                    addGameBinding.title.text.toString(),
                    BoardGameStatus.valueOf(addGameBinding.gameStatus.selectedItem.toString())
                )
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun createNewGame(gameTitle: String, gameStatus: BoardGameStatus) {
        Log.i(TAG, "createNewGame: $gameTitle, $gameStatus")
    }
}