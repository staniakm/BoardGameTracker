package com.mariusz.boardgametracker.ui

import android.R
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mariusz.boardgametracker.adapter.AttendeeAdapter
import com.mariusz.boardgametracker.adapter.GamesAdapter
import com.mariusz.boardgametracker.adapter.SessionAdapter
import com.mariusz.boardgametracker.databinding.ActivityEventBinding
import com.mariusz.boardgametracker.domain.Attendee
import com.mariusz.boardgametracker.domain.BoardGame
import com.mariusz.boardgametracker.domain.Event
import com.mariusz.boardgametracker.domain.EventStatus
import com.mariusz.boardgametracker.viewModels.AttendeeGameViewModel
import com.mariusz.boardgametracker.viewModels.BoardGameViewModel
import com.mariusz.boardgametracker.viewModels.EventViewModel
import com.mariusz.boardgametracker.viewModels.GameSessionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventActivity : AppCompatActivity() {

    private val TAG: String = "Event activity"
    private val eventViewModel: EventViewModel by viewModels()
    private val gameSessionViewModel: GameSessionViewModel by viewModels()
    private val gameViewModel: BoardGameViewModel by viewModels()
    private val attendeeViewModelModel: AttendeeGameViewModel by viewModels()
    private lateinit var binding: ActivityEventBinding
    private lateinit var event: Event

    private lateinit var gameAdapter: GamesAdapter
    private lateinit var attendeeAdapter: AttendeeAdapter
    private lateinit var sessionAdapter: SessionAdapter

    private lateinit var  views: List<ViewBinding>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameAdapter = GamesAdapter { Log.i(TAG, "onCreate: game clicked") }
        attendeeAdapter = AttendeeAdapter { Log.i(TAG, "onCreate: attendee clicked") }
        sessionAdapter = SessionAdapter { game -> startGameSession(game) }
        views = listOf(
            binding.menuView,
            binding.gamesView,
            binding.particpiantsView,
            binding.sessionView
        )
        binding.gamesView.rvGames.layoutManager = LinearLayoutManager(this)
        binding.gamesView.rvGames.adapter = gameAdapter
        binding.particpiantsView.rvParticipants.layoutManager = LinearLayoutManager(this)
        binding.particpiantsView.rvParticipants.adapter = attendeeAdapter
        binding.sessionView.rvSessions.layoutManager = LinearLayoutManager(this)
        binding.sessionView.rvSessions.adapter = sessionAdapter

        intent.extras?.let { extras ->
            event = extras.getSerializable("event") as Event
            binding.eventName.text = event.name
            loadData()
        }

        binding.processEvent.setOnClickListener {
            when (event.eventStatus) {
                EventStatus.SCHEDULED -> startEvent()
                EventStatus.OPEN -> closeEvent()
                EventStatus.CLOSED -> binding.processEvent.visibility == View.GONE
            }
        }

        with(binding.menuView) {
            games.setOnClickListener {
                showHide(binding.gamesView)
            }
            participants.setOnClickListener {
                showHide(binding.particpiantsView)
            }
            sessions.setOnClickListener {
                showHide(binding.sessionView)
            }
        }

        with(binding.particpiantsView) {
            participants.setOnClickListener {
                showHide(binding.menuView)
            }
            setFabEvent(fab) {
                newEventPersonDialog()
            }
        }

        with(binding.gamesView) {
            games.setOnClickListener {
                showHide(binding.menuView)
            }
            setFabEvent(fab) {
                newGameDialog()
            }
        }

        with(binding.sessionView) {
            sessions.setOnClickListener {
                showHide(binding.menuView)
            }
        }
        binding.createSession.setOnClickListener {
            selectGameDialog()
        }
    }

    private fun closeEvent() {
        binding.processEvent.visibility == View.GONE
        eventViewModel.finishEvent(event.id!!)
        event = event.copy(eventStatus = EventStatus.CLOSED)
        loadData()
    }

    private fun startEvent() {
        eventViewModel.startEvent(event.id!!)
        event = event.copy(eventStatus = EventStatus.OPEN)
        loadData()
    }

    @SuppressLint("ResourceType")
    private fun selectGameDialog() {
        val spinner = Spinner(this)
        val games = gameAdapter.getAllGames()
        spinner.adapter = ArrayAdapter(
            this,
            R.layout.simple_spinner_dropdown_item,
            games
        )
        val alert: AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setTitle("Select game session")
            .setView(spinner)
            .setPositiveButton("OK") { _, _ ->
                Log.i(TAG, "newGameDialog: ${spinner.selectedItem as BoardGame}")

                (spinner.selectedItem as BoardGame)?.let {
                    startGameSession(it)
                }
            }
            .setNegativeButton("Cancel") { _, _ -> }
        alert.show()
    }

    private fun startGameSession(it: BoardGame) {
        Intent(this, GameSessionActivity::class.java)
            .apply {
                putExtra("event", event)
                putExtra("game", it)
            }.let {
                startActivity(it)
            }
    }

    @SuppressLint("ResourceType")
    private fun newGameDialog() {
        val spinner = Spinner(this)
        gameViewModel.getAllGames().observe(this) {
            spinner.adapter = ArrayAdapter(
                this,
                R.layout.simple_spinner_dropdown_item,
                it
            )
        }
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
        val attendees = ArrayAdapter<Attendee>(this, R.layout.simple_spinner_dropdown_item)
        attendees.notifyDataSetChanged()

        attendeeViewModelModel.getAllAttendee().observe(this) {
            attendees.addAll(it)
        }

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
                    selectedAttendee = Attendee(tv.text.toString())
                }
                addEventAttendee(selectedAttendee!!)
            }
            .setNegativeButton("Cancel") { _, _ -> {} }
        alert.show()
    }

    private fun addEventAttendee(selectedAttendee: Attendee) {
        if (selectedAttendee.id != null) {
            processEventAttendee(selectedAttendee)
        } else {
            processAttendee(selectedAttendee)
        }
    }

    private fun processEventAttendee(selectedAttendee: Attendee) {
        eventViewModel.addEventAttendee(event.id!!, selectedAttendee.id!!)
        attendeeAdapter.addAttendee(selectedAttendee)
    }

    private fun processAttendee(selectedAttendee: Attendee) {
        attendeeViewModelModel.createNewAttendee(selectedAttendee).observe(this) {
            processEventAttendee(selectedAttendee.copy(id = it.toInt()))
        }
    }

    private fun addNewEventGame(game: BoardGame) {
        eventViewModel.addEventGame(event.id!!, game.id!!)
        gameAdapter.addNewGame(game)
    }

    private fun showHide(view: ViewBinding) {
        views.forEach { it.root.visibility = View.GONE }
        view.root.visibility = View.VISIBLE
    }

    private fun setFabEvent(fab: FloatingActionButton, onClick: () -> Unit) {
        fab.setOnClickListener {
            onClick()
        }
    }

    private fun loadData() {
        binding.eventName.text = event.name
        when (event.eventStatus) {
            EventStatus.SCHEDULED -> {
                binding.createSession.visibility = View.GONE
                binding.processEvent.visibility = View.VISIBLE
                binding.processEvent.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_media_play,
                    0,
                    R.drawable.ic_media_play,
                    0
                )
            }
            EventStatus.OPEN -> {
                binding.createSession.visibility = View.VISIBLE
                binding.processEvent.text = "Zako??cz"
                binding.processEvent.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_media_pause,
                    0,
                    R.drawable.ic_media_pause,
                    0
                )
            }
            EventStatus.CLOSED -> {
                binding.processEvent.visibility = View.GONE
                binding.createSession.visibility = View.GONE
            }
        }
        gameViewModel.getEventGames(event.id!!).observe(this) {
            gameAdapter.submitList(it ?: listOf())
        }
        eventViewModel.getAllAttendeesIds(event.id!!)
            .observe(this) { attendees ->
                attendeeViewModelModel.getAttendees(attendees.map { it.attendeeId }).observe(this) {
                    attendeeAdapter.submitList(it ?: listOf())
                }
            }
        gameSessionViewModel.getSessions(event.id!!)
            .observe(this) { games ->
                sessionAdapter.submitList(games)
            }
    }
}