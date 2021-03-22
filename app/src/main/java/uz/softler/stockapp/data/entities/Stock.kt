package uz.softler.stockapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


data class Stock(
    val country: String,
    val currency: String,
    val exchange: String,
    val finnhubIndustry: String,
    val ipo: String,
    val logo: String,
    val marketCapitalization: Double,
    val name: String,
    val phone: String,
    val shareOutstanding: Double,
    val ticker: String,
    val weburl: String
) {
    var id: Int = 0
}