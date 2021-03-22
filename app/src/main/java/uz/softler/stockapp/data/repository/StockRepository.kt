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

    suspend fun insert(stockSymbol: StockSymbol) {
        localDataSource.insert(stockSymbol)
    }

    fun getAllLikedSymbols(): LiveData<List<StockSymbol>> {
        return localDataSource.getAllLikedSymbols()
    }

    suspend fun remove(stockSymbol: String) {
        localDataSource.remove(stockSymbol)
    }

//    suspend fun update(isLiked: Boolean, id: Int) {
//        localDataSource.update(isLiked, id)
//    }

//    suspend fun getStocksList(list: List<SymbolsItem>): ArrayList<Stock> {
//        val stocks = ArrayList<Stock>()
//
//        for (i in 30..40) {
//            val url =
//                "https://finnhub.io/api/v1/stock/profile2?symbol=${list[0].quotes[i]}&token=c15q95v48v6oal0lpm90"
//            stocks.add(remoteDataSource.getStock(url))
//        }
//
//        return stocks
//    }

    suspend fun getSymbols(url: String): DataWrapper<List<SymbolsItem>> {
        return try {
            val listOfSymbols = remoteDataSource.getSymbols(url) as ArrayList<SymbolsItem>
            DataWrapper.Success(listOfSymbols)
        } catch (e: Exception) {
            DataWrapper.Error(e.message.toString())
        }
    }

    suspend fun getStocks(url: String): DataWrapper<List<StockItem>> {
        return try {
            val stocks = remoteDataSource.getStocks(url).quotes
            DataWrapper.Success(stocks)
        } catch (e: Exception) {
            DataWrapper.Error(e.message.toString())
        }
    }

    suspend fun getActiveStocks(url: String): DataWrapper<List<StockItem>> {
        return try {
            val stocks = remoteDataSource.getActiveStocks(url).quotes
            DataWrapper.Success(stocks)
        } catch (e: Exception) {
            DataWrapper.Error(e.message.toString())
        }
    }
}