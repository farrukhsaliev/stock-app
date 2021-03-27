package uz.softler.stockapp.data.entities

import androidx.room.Entity

data class LookUpStock(
    val count: Int,
    val result: List<Result>
)