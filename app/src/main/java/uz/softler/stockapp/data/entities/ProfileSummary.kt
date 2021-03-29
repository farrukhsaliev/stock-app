package uz.softler.stockapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "profiles_table")
data class ProfileSummary(
        @PrimaryKey
        val symbol: String,
        val country: String? = "",
        val phone: String = "",
        val website: String = "",
        val fullTimeEmployees: String = "",
        val longBusinessSummary: String = ""
) {
    @SerializedName("id")
    var id: Int? = null
}
