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
import com.mariusz.boardgametracker.R
import com.mariusz.boardgametracker.adapter.EventAdapter
import com.mariusz.boardgametracker.databinding.ActivityMainBinding
import com.mariusz.boardgametracker.databinding.AddEventViewBinding
import com.mariusz.boardgametracker.functions.toLocalDate
import com.mariusz.boardgametracker.viewModels.EventViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val eventViewModel: EventViewModel by viewModels()
    private val TAG: String = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private lateinit var addEventBinding: AddEventViewBinding
    private lateinit var eventAdapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        addEventBinding = AddEventViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        eventAdapter = EventAdapter()
        binding.rvEvent.layoutManager = LinearLayoutManager(this)
        binding.rvEvent.adapter = eventAdapter
//        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener {
            newEventDialog()
        }
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
        //open event indent
        eventViewModel.getEvents().let {
            it.forEach { event ->
                println(event)
            }
            eventAdapter.submitList(it)
        }
        startActivity(Intent(this, EventActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}