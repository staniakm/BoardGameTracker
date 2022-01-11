package com.mariusz.boardgametracker.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mariusz.boardgametracker.GameListActivity
import com.mariusz.boardgametracker.R
import com.mariusz.boardgametracker.adapter.EventAdapter
import com.mariusz.boardgametracker.databinding.ActivityMainBinding
import com.mariusz.boardgametracker.databinding.AddEventViewBinding
import com.mariusz.boardgametracker.domain.Event
import com.mariusz.boardgametracker.functions.toLocalDate
import com.mariusz.boardgametracker.viewModels.EventViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG: String = "MainActivity"
    private val eventViewModel: EventViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var addEventBinding: AddEventViewBinding
    private lateinit var eventAdapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        addEventBinding = AddEventViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        eventAdapter = EventAdapter(onItemClick)
        binding.rvEvent.layoutManager = LinearLayoutManager(this)
        binding.rvEvent.adapter = eventAdapter
        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener {
            newEventDialog()
        }
        loadData()
    }

    private fun loadData() {
        eventViewModel.getEvents().let {
            eventAdapter.submitList(it)
        }

    }

    private val onItemClick: (event: Event) -> Unit = { event: Event ->
        val intent = Intent(this, EventActivity::class.java)
        intent.putExtra("event", event)
        startActivity(intent)
    }

    @SuppressLint("ResourceType")
    private fun newEventDialog() {
        addEventBinding.root.parent?.let {
            (it as ViewGroup).removeView(addEventBinding.root)
        }
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Create new event")

        builder.setView(addEventBinding.root)
        builder.setPositiveButton("OK") { _, _ ->
            if (addEventBinding.description.text.isNotBlank()) {
                createNewEvent(
                    addEventBinding.description.text.toString(),
                    addEventBinding.date.toLocalDate()
                )
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun createNewEvent(eventName: String, eventDate: LocalDate) {
        Log.i(TAG, "createNewEvent: $eventName $eventDate")
        //save new event in database
        eventViewModel.storeEvent(eventName, eventDate)
            .let {
                eventAdapter.addNewEvent(it)
                val intent = Intent(this, EventActivity::class.java)
                intent.putExtra("event", it)
                startActivity(intent)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_games -> openGameListView()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openGameListView(): Boolean {
        Intent(this, GameListActivity::class.java).let {
            startActivity(it)
        }
        return true
    }
}