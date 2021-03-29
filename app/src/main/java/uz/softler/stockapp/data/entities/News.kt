package uz.softler.stockapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "news_table")
data class News(
    val category: String,
    val datetime: Int,
    val headline: String,
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    val image: String,
    val related: String,
    val source: String,
    val summary: String,
    val url: String
)