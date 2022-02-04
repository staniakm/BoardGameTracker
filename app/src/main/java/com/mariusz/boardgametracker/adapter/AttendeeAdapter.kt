package com.mariusz.boardgametracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mariusz.boardgametracker.databinding.AttendeeAdapterBinding
import com.mariusz.boardgametracker.domain.Attendee

class AttendeeAdapter(private val onItemClick: (game: Attendee) -> Unit) :
    RecyclerView.Adapter<AttendeeAdapter.AttendeeAdapterViewHolder>() {
    inner class AttendeeAdapterViewHolder(val binding: AttendeeAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    private val diffCallback = object : DiffUtil.ItemCallback<Attendee>() {
        override fun areItemsTheSame(oldItem: Attendee, newItem: Attendee): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Attendee, newItem: Attendee): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)
    fun submitList(list: List<Attendee>) = differ.submitList(list)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AttendeeAdapter.AttendeeAdapterViewHolder {
        val binding =
            AttendeeAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AttendeeAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: AttendeeAdapter.AttendeeAdapterViewHolder,
        position: Int
    ) {
        val item = differ.currentList[position]
        holder.binding.apply {
            name.text = item.name

            eventLayout.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun addAttendee(attendee: Attendee) {
        differ.currentList.toMutableList().apply {
            add(attendee)
        }.let {
            submitList(it.toList())
        }
    }
}
