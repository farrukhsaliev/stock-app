package uz.softler.stockapp.ui.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import uz.softler.stockapp.data.entities.*
import uz.softler.stockapp.data.repository.StockRepository
import uz.softler.stockapp.utils.DataWrapper
import uz.softler.stockapp.utils.Strings

class PagerItemViewModel @ViewModelInject constructor(
        private val repository: StockRepository
) : ViewModel() {

//    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private var _stocksLiveData = MutableLiveData<List<StockItem>>()
    private val stocksLiveData: LiveData<List<StockItem>> = _stocksLiveData

    private var _stocksFromDbLiveData = MutableLiveData<List<StockItem>>()
    val stocksFromDbLiveData: LiveData<List<StockItem>> = _stocksFromDbLiveData

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun insert(stocks: List<StockItem>) {
        viewModelScope.launch {
            repository.insert(stocks)
        }
    }

//    fun remove(stockSymbol: String) {
//        viewModelScope.launch {
//            repository.remove(stockSymbol)
//        }
//    }

    fun update(isLiked: Boolean, symbol: String) {
        viewModelScope.launch {
            repository.update(isLiked, symbol)
        }
    }

    fun getStocksFromDb() {
        _stocksFromDbLiveData.postValue(repository.getAllLikedStocks().value)
    }

    fun getStocksRemote(value: String, isSend: Boolean): LiveData<List<StockItem>> {
        if (!isSend) {
            viewModelScope.launch {
                _isLoading.postValue(true)
                repository.getStocks("https://mboum.com/api/v1/co/collections/?list=$value&start=1&apikey=${Strings.MOBIUM_API_KEY}").also {
                    when (it) {
                        is DataWrapper.Success -> {
                            _stocksLiveData.postValue(it.data)
                            Log.d("THISAPP", "error: ${it.data}")
                        }
                        is DataWrapper.Error -> {
                            Log.d("THISAPP", "error: ${it.errorMessage}")
                        }
                    }
                }
                _isLoading.postValue(false)
            }
        }
        return stocksLiveData
    }
}