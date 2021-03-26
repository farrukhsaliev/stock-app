package uz.softler.stockapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import uz.softler.stockapp.data.entities.*
import uz.softler.stockapp.data.local.StockDao
import uz.softler.stockapp.data.remote.JsonPlaceHolder
import uz.softler.stockapp.utils.DataWrapper
import javax.inject.Inject

class StockRepository @Inject constructor(
    private val remoteDataSource: JsonPlaceHolder,
    private val localDataSource: StockDao
) {

    // Dao

    suspend fun insert(stocks: List<StockItem>) {
        localDataSource.insert(stocks)
    }

//    suspend fun remove(stockSymbol: String) {
//        localDataSource.remove(stockSymbol)
//    }

    suspend fun update(isLiked: Boolean, symbol: String) {
        localDataSource.update(isLiked, symbol)
    }

    fun getAllLikedStocks(): LiveData<List<StockItem>> {
        return localDataSource.getAllLikedStocks()
    }

    suspend fun getNews(): DataWrapper<List<News>> {
        return try {
            DataWrapper.Success(remoteDataSource.getNews())
        } catch (e: Exception) {
            DataWrapper.Error(e.message.toString())
        }
    }




    // JsonPlaceHolder

    suspend fun getStocks(url: String): DataWrapper<List<StockItem>> {
        return try {
            val stocks = remoteDataSource.getStocks(url).quotes
            DataWrapper.Success(stocks)
        } catch (e: Exception) {
            DataWrapper.Error(e.message.toString())
        }
    }

}