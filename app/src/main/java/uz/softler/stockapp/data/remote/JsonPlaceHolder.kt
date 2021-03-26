package uz.softler.stockapp.data.remote

import androidx.lifecycle.LiveData
import retrofit2.http.GET
import retrofit2.http.Url
import uz.softler.stockapp.data.entities.*

interface JsonPlaceHolder {

    @GET
    suspend fun getStocks(@Url url: String): Stocks

    @GET("news?category=general&token=c15q95v48v6oal0lpm90")
    suspend fun getNews(): List<News>

}