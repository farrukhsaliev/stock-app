package uz.softler.stockapp.data.entities

data class Symbol(
    val currency: String,
    val description: String,
    val displaySymbol: String,
    val figi: String,
    val mic: String,
    val symbol: String,
    val type: String
)