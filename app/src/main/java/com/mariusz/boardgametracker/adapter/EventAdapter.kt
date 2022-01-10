package com.mariusz.boardgametracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mariusz.boardgametracker.R
import com.mariusz.boardgametracker.databinding.EventAdapterBinding
import com.mariusz.boardgametracker.domain.Event
import com.mariusz.boardgametracker.domain.EventStatus

class EventAdapter : RecyclerView.Adapter<EventAdapter.EventAdapterViewHolder>() {

    inner class EventAdapterViewHolder(val binding: EventAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    private val diffCallback = object : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)
    fun submitList(list: List<Event>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapterViewHolder {
        val binding =
            EventAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventAdapterViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.binding.apply {
            date.text = item.date.toString()
            name.text = item.name
            eventIcon.setImageResource(
                when (item.eventStatus) {
                    EventStatus.SCHEDULED -> R.drawable.event_scheduled
                    EventStatus.OPEN -> R.drawable.event_open
                    EventStatus.CLOSED -> R.drawable.event_finished
                }
            )
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}