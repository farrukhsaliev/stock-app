package uz.softler.stockapp.data.remote

import androidx.lifecycle.LiveData
import retrofit2.http.GET
import retrofit2.http.Url
import uz.softler.stockapp.data.entities.*

interface JsonPlaceHolder {
    @GET
    suspend fun getStock(@Url url: String): Stock

    @GET
    suspend fun getSymbols(@Url url: String): LiveData<List<SymbolsItem>>

    @GET
    suspend fun getStocks(@Url url: String): Stocks

    @GET
    suspend fun getActiveStocks(@Url url: String): Stocks
}