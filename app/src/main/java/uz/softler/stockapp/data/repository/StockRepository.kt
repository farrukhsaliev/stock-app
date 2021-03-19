package uz.softler.stockapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import androidx.lifecycle.viewModelScope
import retrofit2.Callback
import retrofit2.Response
import uz.softler.stockapp.data.entities.Stock
import uz.softler.stockapp.data.local.StockDao
import uz.softler.stockapp.data.remote.JsonPlaceHolder
import javax.inject.Inject

class StockRepository @Inject constructor(
        private val remoteDataSource: JsonPlaceHolder,
        private val localDataSource: StockDao
) {

    private var _stocksLiveData = MutableLiveData<Stock>()

    suspend fun insert(stock: Stock) {
        localDataSource.insert(stock)
    }

    fun getAllLikedStocks(): LiveData<List<Stock>> {
        return localDataSource.getAllLikedStocks()
    }

    suspend fun unlikeStock(ticker: String) {
        localDataSource.unlikeStock(ticker)
    }

    //    fun getStock(ticker: String) = remoteDataSource.getStock(ticker)
//    fun getStock(): Call<Stock> {
//        return remoteDataSource.getStock()
//    }

    fun getStock(): MutableLiveData<Stock> {
        val call = remoteDataSource.getStock()

        call.enqueue(object: Callback<Stock> {
            override fun onFailure(call: Call<Stock>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                    call: Call<Stock>,
                    response: Response<Stock>
            ) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", response.body().toString())

                val data = response.body()

                _stocksLiveData.value = data!!
            }
        })

        return _stocksLiveData
    }
}