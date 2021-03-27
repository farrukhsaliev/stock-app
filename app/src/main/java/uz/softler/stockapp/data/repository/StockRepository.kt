package uz.softler.stockapp.data.repository

import kotlinx.coroutines.flow.Flow
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

    fun getAllSectionStocks(value: String): Flow<List<StockItem>> {
        return localDataSource.getAllSectionStocks(value)
    }

    fun getAllLikedStocks(): Flow<List<StockItem>> {
        return localDataSource.getAllLikedStocks()
    }

//    suspend fun getPagingStocks(url: String) =
//        remoteDataSource.getPagingStocks(url)

//    fun getStocksList(url: String): LiveData<PagingData<StockItem>> {
//        return Pager(
//            PagingConfig(
//                pageSize = 20,
//                maxSize = 100,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = { StockPagingSource(remoteDataSource, url) }
//        ).liveData
//    }

    // JsonPlaceHolder
    suspend fun getStocks(url: String): DataWrapper<List<StockItem>> {
        return try {
            DataWrapper.Success(remoteDataSource.getStocks(url).quotes)
        } catch (e: Exception) {
            DataWrapper.Error(e.message.toString())
        }
    }
}