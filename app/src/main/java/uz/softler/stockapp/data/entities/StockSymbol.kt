package uz.softler.stockapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stocks_table")
data class StockSymbol(
        val symbol: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}