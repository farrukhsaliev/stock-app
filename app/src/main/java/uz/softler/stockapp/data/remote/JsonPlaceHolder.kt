package uz.softler.stockapp.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import uz.softler.stockapp.data.entities.*
import uz.softler.stockapp.utils.Strings

interface JsonPlaceHolder {

    @GET
    suspend fun getStocks(@Url url: String): StocksResponse

    @GET("news?category=general&token=${Strings.FINNHUB_TOKEN}")
    suspend fun getNews(): List<News>

    @GET("search?q={symbol}&token=${Strings.FINNHUB_TOKEN}")
    suspend fun getLookUpStock(@Path("symbol") symbol: String): List<LookUpStock>

    @GET
    fun getLogo(@Url url: String): Logos

//    @GET
//    suspend fun getPagingStocks(@Url url: String): StocksResponse
}