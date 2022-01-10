package com.mariusz.boardgametracker.functions

import android.widget.DatePicker
import java.time.LocalDate

fun DatePicker.toLocalDate(): LocalDate = LocalDate.of(this.year, this.month + 1, this.dayOfMonth)
