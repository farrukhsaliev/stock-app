package uz.softler.stockapp.data.entities

data class Stocks(
    val count: Int,
    val description: String,
    val quotes: List<StockItem>,
    val start: Int,
    val total: Int
)