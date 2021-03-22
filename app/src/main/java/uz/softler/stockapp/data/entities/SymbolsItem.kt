package uz.softler.stockapp.data.entities

data class SymbolsItem(
    val count: Int,
    val jobTimestamp: Long,
    val quotes: List<String>,
    val startInterval: Long
)