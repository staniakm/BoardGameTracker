package com.mariusz.boardgametracker.ui

import android.R
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mariusz.boardgametracker.adapter.AttendeeAdapter
import com.mariusz.boardgametracker.adapter.GamesAdapter
import com.mariusz.boardgametracker.databinding.ActivityEventBinding
import com.mariusz.boardgametracker.domain.Attendee
import com.mariusz.boardgametracker.domain.BoardGame
import com.mariusz.boardgametracker.domain.Event
import com.mariusz.boardgametracker.domain.EventStatus
import com.mariusz.boardgametracker.viewModels.AttendeeGameViewModel
import com.mariusz.boardgametracker.viewModels.BoardGameViewModel
import com.mariusz.boardgametracker.viewModels.EventViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventActivity : AppCompatActivity() {

    private val TAG: String = "Event activity"
    private val eventViewModel: EventViewModel by viewModels()
    private val gameViewModelModel: BoardGameViewModel by viewModels()
    private val attendeeViewModelModel: AttendeeGameViewModel by viewModels()
    private lateinit var binding: ActivityEventBinding
    private lateinit var event: Event

    private lateinit var gameAdapter: GamesAdapter
    private lateinit var attendeeAdapter: AttendeeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameAdapter = GamesAdapter { Log.i(TAG, "onCreate: game clicked") }
        attendeeAdapter = AttendeeAdapter { Log.i(TAG, "onCreate: attendee clicked") }

        binding.rvGames.layoutManager = LinearLayoutManager(this)
        binding.rvGames.adapter = gameAdapter
        binding.rvParticipants.layoutManager = LinearLayoutManager(this)
        binding.rvParticipants.adapter = attendeeAdapter

        intent.extras?.let { extras ->
            event = extras.getSerializable("event") as Event
            loadData()
        }

        binding.startEvent.setOnClickListener {
            eventViewModel.startEvent(event.id!!)
            event = event.copy(eventStatus = EventStatus.OPEN)
            loadData()
        }

        binding.finishEvent.setOnClickListener {
            eventViewModel.finishEvent(event.id!!)
            event = event.copy(eventStatus = EventStatus.CLOSED)
            loadData()
        }

        binding.participants.setOnClickListener {
            showHide(binding.games, binding.rvParticipants)
            setFabEvent {
                newEventPersonDialog()
            }
        }

        binding.games.setOnClickListener {
            showHide(binding.participants, binding.rvGames)
            setFabEvent {
                newGameDialog()
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun newGameDialog() {
        val games = gameViewModelModel.getAllGames()
        val spinner = Spinner(this)
        spinner.adapter = ArrayAdapter(
            this,
            R.layout.simple_spinner_dropdown_item,
            games
        )
        val alert: AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setTitle("Select game")
            .setView(spinner)
            .setPositiveButton("OK") { _, _ ->
                Log.i(TAG, "newGameDialog: ${spinner.selectedItem as BoardGame}")

                (spinner.selectedItem as BoardGame)?.let {
                    addNewEventGame(it)
                }
            }
            .setNegativeButton("Cancel") { _, _ -> }
        alert.show()
    }

    @SuppressLint("ResourceType")
    private fun newEventPersonDialog() {
        var selectedAttendee: Attendee? = null
        val attendeeList = attendeeViewModelModel.getAllAttendee()
        val attendees = ArrayAdapter<Attendee>(this, R.layout.simple_spinner_dropdown_item)
        attendees.notifyDataSetChanged()
        attendees.addAll(attendeeList)
        val tv = AutoCompleteTextView(this)

        tv.setAdapter(attendees)
        tv.setSelection(0)

        tv.setOnItemClickListener { parent, view, position, id ->
            selectedAttendee = parent.getItemAtPosition(position) as Attendee
        }
        val alert: AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setTitle("Select attendee")
            .setView(tv)
            .setPositiveButton("OK") { _, _ ->
                if (selectedAttendee == null) {
                    selectedAttendee = Attendee(-1, tv.text.toString())
                }
                addEventAttendee(selectedAttendee!!)
            }
            .setNegativeButton("Cancel") { _, _ -> {} }
        alert.show()
    }

    private fun addEventAttendee(selectedAttendee: Attendee) {
        processAttendee(selectedAttendee).let {
            eventViewModel.addEventAttendee(event.id!!, it)
            attendeeAdapter.addAttendee(it)
        }
    }

    private fun processAttendee(selectedAttendee: Attendee): Attendee {
        return if (selectedAttendee.id == -1) {
            attendeeViewModelModel.createNewAttendee(selectedAttendee)
        } else {
            selectedAttendee
        }
    }

    private fun addNewEventGame(game: BoardGame) {
        eventViewModel.addEventGame(event.id!!, game.id)
        gameAdapter.addNewGame(game)
    }

    private fun showHide(button: Button, rvToShow: RecyclerView) {
        if (button.visibility == View.GONE) {
            button.visibility = View.VISIBLE
            rvToShow.visibility = View.GONE
        } else {
            button.visibility = View.GONE
            rvToShow.visibility = View.VISIBLE
            binding.fab.visibility = View.VISIBLE
        }
    }

    private fun setFabEvent(onClick: () -> Unit) {
        binding.fab.setOnClickListener {
            onClick()
        }
    }

    private fun loadData() {
        binding.eventName.text = event.name
        when (event.eventStatus) {
            EventStatus.SCHEDULED -> binding.finishEvent.visibility = View.GONE
            EventStatus.OPEN -> binding.startEvent.visibility = View.GONE
            EventStatus.CLOSED -> {
                binding.finishEvent.visibility = View.GONE
                binding.startEvent.visibility = View.GONE
            }
        }
        gameViewModelModel.getEventGames(event.id!!).observe(this) {
            gameAdapter.submitList(it)
        }
        eventViewModel.getAllAttendeesIds(event.id!!).let {
            attendeeAdapter.submitList(attendeeViewModelModel.getAttendees(it))
        }

    }
}