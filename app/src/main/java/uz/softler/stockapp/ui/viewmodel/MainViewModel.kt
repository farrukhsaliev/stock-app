package uz.softler.stockapp.ui.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import uz.softler.stockapp.data.entities.Stock
import uz.softler.stockapp.data.repository.StockRepository


class MainViewModel @ViewModelInject constructor(
        private val repository: StockRepository
) : ViewModel() {

    private var _stocksLiveData = MutableLiveData<Stock>()
//    val stocksLiveData: LiveData<Call<Stock>> = _stocksLiveData

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

    fun getStock() : LiveData<Stock> {
        _stocksLiveData = repository.getStock()
        return _stocksLiveData
    }

}