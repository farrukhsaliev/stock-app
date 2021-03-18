package uz.softler.stockapp.db.models

data class Stock(
    var tiker: String,
    var companyName: String,
    var logo: Int,
    var changes: String,
    var price: String
)
