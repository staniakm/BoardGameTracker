package com.mariusz.boardgametracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mariusz.boardgametracker.databinding.UserScoreAdapterBinding
import com.mariusz.boardgametracker.domain.Attendee

class UserScoreAdapter() :
    RecyclerView.Adapter<UserScoreAdapter.UserScoreAdapterViewHolder>() {
    inner class UserScoreAdapterViewHolder(val binding: UserScoreAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    private val diffCallback = object : DiffUtil.ItemCallback<Attendee>() {
        override fun areItemsTheSame(oldItem: Attendee, newItem: Attendee): Boolean {
            return oldItem.name == newItem.name
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
    ): UserScoreAdapter.UserScoreAdapterViewHolder {
        val binding =
            UserScoreAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserScoreAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: UserScoreAdapter.UserScoreAdapterViewHolder,
        position: Int
    ) {
        val item = differ.currentList[position]
        holder.binding.apply {
            userName.text = item.name
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
