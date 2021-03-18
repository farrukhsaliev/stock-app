package uz.softler.stockapp.db.models

import java.io.Serializable

data class Page(
    var title: String,
    var items: List<Stock>
): Serializable
