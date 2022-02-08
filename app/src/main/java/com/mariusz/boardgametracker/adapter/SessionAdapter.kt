package com.mariusz.boardgametracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mariusz.boardgametracker.databinding.AttendeeAdapterBinding
import com.mariusz.boardgametracker.databinding.GameAdapterBinding
import com.mariusz.boardgametracker.domain.Attendee
import com.mariusz.boardgametracker.domain.BoardGame

class SessionAdapter(private val onItemClick: (game: BoardGame) -> Unit) :
    RecyclerView.Adapter<SessionAdapter.SessionAdapterViewHolder>() {
    inner class SessionAdapterViewHolder(val binding: GameAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    private val diffCallback = object : DiffUtil.ItemCallback<BoardGame>() {
        override fun areItemsTheSame(oldItem: BoardGame, newItem: BoardGame): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: BoardGame, newItem: BoardGame): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)
    fun submitList(list: List<BoardGame>) = differ.submitList(list)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SessionAdapter.SessionAdapterViewHolder {
        val binding =
            GameAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SessionAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SessionAdapter.SessionAdapterViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.binding.apply {
            gameName.text = item.name

            eventLayout.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun addNewGame(game: BoardGame) {
        differ.currentList.toMutableList().apply {
            if (this.none { it.name.equals(game.name, true) })
                add(game)
        }.let {
            submitList(it.toList())
        }
    }

    fun getAllGames(): List<BoardGame> {
        return differ.currentList.toList()
    }
}
