package uz.softler.stockapp.data.entities

import java.io.Serializable

data class Page(
        var title: String,
        var items: ArrayList<StockItem>
): Serializable
