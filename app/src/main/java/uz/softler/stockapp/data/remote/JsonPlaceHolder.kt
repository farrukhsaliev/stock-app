package uz.softler.stockapp.data.remote

import retrofit2.http.GET
import uz.softler.stockapp.data.entities.Stock
import uz.softler.stockapp.data.entities.Symbol

interface JsonPlaceHolder {

    @GET("profile2?symbol=AAPL&token=c15q95v48v6oal0lpm90")
    suspend fun getStock(): Stock

    @GET("symbol?exchange=US&token=c15q95v48v6oal0lpm90")
    suspend fun getSymbols(): List<Symbol>

}