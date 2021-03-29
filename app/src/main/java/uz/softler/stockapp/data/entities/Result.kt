package uz.softler.stockapp.data.entities

import com.google.gson.annotations.SerializedName

data class Result(
    val description: String,
    val displaySymbol: String,
    val symbol: String,
    val type: String
) {
    @SerializedName("id")
    var id: Int? = null
}
