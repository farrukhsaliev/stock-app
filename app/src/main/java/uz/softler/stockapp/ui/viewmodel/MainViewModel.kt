package uz.softler.stockapp.ui.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import uz.softler.stockapp.data.entities.Stock
import uz.softler.stockapp.data.repository.StockRepository
import uz.softler.stockapp.utils.DataWrapper


class MainViewModel @ViewModelInject constructor(
        private val repository: StockRepository
) : ViewModel() {

    private var _stocksLiveData = MutableLiveData<Stock>()
    val stocksLiveData: LiveData<Stock> = _stocksLiveData
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun insert(stock: Stock) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(stock)
        }
    }

    fun getAllLikedStocks(): LiveData<List<Stock>> {
        return repository.getAllLikedStocks()
    }

    fun unlikeStock(ticker: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.unlikeStock(ticker)
        }
    }

    //    fun getStock(ticker: String) = repository.getStock(ticker)
//    fun getStock(): Call<Stock> {
//        return repository.getStock()
//    }

//    fun getStock() : LiveData<Stock> {
//        Log.d("REQUEST", "Send")
////        _stocksLiveData = repository.getStock()
//        return _stocksLiveData
//    }

    init {
        coroutineScope.launch {
            repository.getStock().also {
                when (it) {
                    is DataWrapper.Success -> {
                        _stocksLiveData.postValue(it.data)
                    }
                    is DataWrapper.Error -> {
                        Log.e("MyException", "ViewModel: error occured!")
                    }
                }
            }
        }
    }
}