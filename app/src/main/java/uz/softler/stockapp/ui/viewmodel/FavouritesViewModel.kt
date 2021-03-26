package uz.softler.stockapp.ui.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.softler.stockapp.data.entities.StockItem
import uz.softler.stockapp.data.repository.StockRepository
import uz.softler.stockapp.utils.DataWrapper
import uz.softler.stockapp.utils.Strings

class FavouritesViewModel @ViewModelInject constructor(
        private val repository: StockRepository
) : ViewModel() {

//    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private var _stocksLiveData = MutableLiveData<List<StockItem>>()
    val stocksLiveData: LiveData<List<StockItem>> = _stocksLiveData

    fun insert(stocks: List<StockItem>) {
        viewModelScope.launch {
            repository.insert(stocks)
        }
    }

//    fun remove(stockSymbol: String) {
//         viewModelScope.launch {
//            repository.remove(stockSymbol)
//        }
//    }

    fun getAllLikedStocks(): LiveData<List<StockItem>> {
        return repository.getAllLikedStocks()
    }

//    fun update(isLiked: Boolean, symbol: String) {
//        viewModelScope.launch {
//            repository.update(isLiked, symbol)
//        }
//    }

    init {
        viewModelScope.launch {
            _stocksLiveData.postValue(repository.getAllLikedStocks().value)
        }
    }
}