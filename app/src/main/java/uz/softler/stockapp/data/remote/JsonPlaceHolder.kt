package uz.softler.stockapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import uz.softler.stockapp.data.entities.*
import uz.softler.stockapp.utils.Strings

interface JsonPlaceHolder {

    @GET
    suspend fun getStocks(@Url url: String): StocksResponse

    @GET
    suspend fun getProfile(@Url url: String): CompanyProfile

    @GET("news?category=general&token=${Strings.FINNHUB_TOKEN}")
    suspend fun getNews(): List<News>

    @GET("search?&token=${Strings.FINNHUB_TOKEN}")
    suspend fun getLookUpStock(@Query("q") symbol: String): LookUpStock

}