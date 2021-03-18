package uz.softler.stockapp.data.repository

import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow
import uz.softler.stockapp.data.entities.Stock
import uz.softler.stockapp.data.local.StockDao
import uz.softler.stockapp.data.remote.JsonPlaceHolder
import javax.inject.Inject

class StockRepository @Inject constructor(
    private val remoteDataSource: JsonPlaceHolder,
    private val localDataSource: StockDao
) {
    suspend fun insert(stock: Stock) {
        localDataSource.insert(stock)
    }

    fun getAllLikedStocks(): Flow<List<Stock>> {
        return localDataSource.getAllLikedStocks()
    }

    suspend fun unlikeStock(ticker: String) {
        localDataSource.unlikeStock(ticker)
    }

//    fun getStock(ticker: String) = remoteDataSource.getStock(ticker)
    fun getStock() = remoteDataSource.getStock()

}