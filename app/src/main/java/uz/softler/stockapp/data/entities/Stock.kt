package uz.softler.stockapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stocks_table")
data class Stock(
    val country: String,
    val currency: String,
    val exchange: String,
    val finnhubIndustry: String,
    val ipo: String,
    val logo: String,
    val marketCapitalization: Int,
    val name: String,
    val phone: String,
    val shareOutstanding: Double,
    val ticker: String,
    val weburl: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}