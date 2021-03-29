package uz.softler.stockapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "stocks_table")
data class StockItem(
        val ask: Double,
        val askSize: Int,
        val averageDailyVolume10Day: Int,
        val averageDailyVolume3Month: Int,
        val bid: Double,
        val bidSize: Int,
        val bookValue: Double,
        val currency: String,
        val displayName: String?,
        val dividendDate: Int,
        val earningsTimestamp: Int,
        val earningsTimestampEnd: Int,
        val earningsTimestampStart: Int,
        val epsCurrentYear: Double,
        val epsForward: Double,
        val epsTrailingTwelveMonths: Double,
        val esgPopulated: Boolean,
        val exchange: String,
        val exchangeDataDelayedBy: Int,
        val exchangeTimezoneName: String,
        val exchangeTimezoneShortName: String,
        val fiftyDayAverage: Double,
        val fiftyDayAverageChange: Double,
        val fiftyDayAverageChangePercent: Double,
        val fiftyTwoWeekHigh: Double,
        val fiftyTwoWeekHighChange: Double,
        val fiftyTwoWeekHighChangePercent: Double,
        val fiftyTwoWeekLow: Double,
        val fiftyTwoWeekLowChange: Double,
        val fiftyTwoWeekLowChangePercent: Double,
        val fiftyTwoWeekRange: String,
        val financialCurrency: String?,
        val firstTradeDateMilliseconds: Long,
        val forwardPE: Double,
        val fullExchangeName: String,
        val gmtOffSetMilliseconds: Int,
        val language: String,
        val longName: String,
        val market: String,
        val marketCap: Long,
        val marketState: String,
        val messageBoardId: String,
        val postMarketChange: Double,
        val postMarketChangePercent: Double,
        val postMarketPrice: Double,
        val postMarketTime: Int,
        val priceEpsCurrentYear: Double,
        val priceHint: Int,
        val priceToBook: Double,
        val quoteSourceName: String,
        val quoteType: String,
        val region: String,
        val regularMarketChange: Double,
        val regularMarketChangePercent: Double,
        val regularMarketDayHigh: Double,
        val regularMarketDayLow: Double,
        val regularMarketDayRange: String,
        val regularMarketOpen: Double,
        val regularMarketPreviousClose: Double,
        val regularMarketPrice: Double,
        val regularMarketTime: Int,
        val regularMarketVolume: Int,
        val sharesOutstanding: Long,
        val shortName: String,
        val sourceInterval: Int,
        val tradeable: Boolean,
        val trailingAnnualDividendRate: Double,
        val trailingAnnualDividendYield: Double,
        val trailingPE: Double,
        val triggerable: Boolean,
        val twoHundredDayAverage: Double,
        val twoHundredDayAverageChange: Double,
        val twoHundredDayAverageChangePercent: Double,
        var logo: String? = "",
        @PrimaryKey
        val symbol: String,
        var isLiked: Boolean = false,
        var section: String = ""
) : Serializable {
    @SerializedName("id")
    var id: Int? = null
}