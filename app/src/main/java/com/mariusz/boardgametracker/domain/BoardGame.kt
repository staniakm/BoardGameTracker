package com.mariusz.boardgametracker.domain

import com.mariusz.boardgametracker.R

data class BoardGame(
    val id: Int,
    val name: String,
    val gameStatus: BoardGameStatus = BoardGameStatus.HOME
) {
    override fun toString(): String {
        return name
    }
}

enum class BoardGameStatus(val iconId: Int) {

    HOME(R.drawable.game_status_home), BORROW(R.drawable.game_status_borrow)

}