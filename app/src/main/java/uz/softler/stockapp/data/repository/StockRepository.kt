package uz.softler.stockapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.softler.stockapp.data.entities.Stock
import uz.softler.stockapp.data.entities.Symbol
import uz.softler.stockapp.data.local.StockDao
import uz.softler.stockapp.data.remote.JsonPlaceHolder
import uz.softler.stockapp.utils.DataWrapper
import javax.inject.Inject

class StockRepository @Inject constructor(
        private val remoteDataSource: JsonPlaceHolder,
        private val localDataSource: StockDao
) {

    suspend fun insert(stock: Stock) {
        localDataSource.insert(stock)
    }

    fun getAllLikedStocks(): LiveData<List<Stock>> {
        return localDataSource.getAllLikedStocks()
    }

    suspend fun unlikeStock(ticker: String) {
        localDataSource.unlikeStock(ticker)
    }

    suspend fun getStock(): DataWrapper<Stock> {
        return try {
            val data = remoteDataSource.getStock()
            DataWrapper.Success(data)
        } catch (e: Exception) {
            DataWrapper.Error(e.message.toString())
        }
    }

    suspend fun getSymbols(): DataWrapper<List<Symbol>> {
        return try {
            val data = remoteDataSource.getSymbols()
            DataWrapper.Success(data)
        } catch (e: Exception) {
            DataWrapper.Error(e.message.toString())
        }
    }
}