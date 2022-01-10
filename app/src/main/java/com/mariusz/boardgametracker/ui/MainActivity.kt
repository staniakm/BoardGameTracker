package com.mariusz.boardgametracker.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mariusz.boardgametracker.R
import com.mariusz.boardgametracker.adapter.EventAdapter
import com.mariusz.boardgametracker.databinding.ActivityMainBinding
import com.mariusz.boardgametracker.viewModels.EventViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val eventViewModel: EventViewModel by viewModels()
    private val TAG: String = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private lateinit var eventAdapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        eventAdapter = EventAdapter()
        binding.rvEvent.layoutManager = LinearLayoutManager(this)
        binding.rvEvent.adapter = eventAdapter
//        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener {
            newEventDialog()
        }
    }

    private fun newEventDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Create new event")
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)
        builder.setPositiveButton("OK") { _, _ ->
            input.text.toString().let {
                if (it.isNotBlank()) {
                    createNewEvent(it.uppercase())
                }
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun createNewEvent(eventName: String) {
        Log.i(TAG, "createNewEvent: $eventName")
        //save new event in database
        eventViewModel.storeEvent(eventName)
        //open event indent
        eventViewModel.getEvents().let {
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