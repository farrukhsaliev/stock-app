package uz.softler.stockapp.data.remote

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import uz.softler.stockapp.data.entities.Stock

interface JsonPlaceHolder {
//    @GET("character")
//    suspend fun getAllCharacters() : Response<CharacterList>
//
//    @GET("character/{id}")
//    suspend fun getCharacter(@Path("id") id: Int): Response<Character>

//    @GET("v1/stock/profile2?symbol=AAPL&token=c15q95v48v6oal0lpm90")
//    fun getStock(@Path("ticker") ticker: String): Call<Stock>

    @GET("profile2?symbol=AAPL&token=c15q95v48v6oal0lpm90")
    fun getStock(): Call<Stock>

}