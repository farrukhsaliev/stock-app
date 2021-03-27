package uz.softler.stockapp.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import uz.softler.stockapp.data.entities.StockItem
import uz.softler.stockapp.data.repository.StockRepository

class FavouritesViewModel @ViewModelInject constructor(
        private val repository: StockRepository
) : ViewModel() {

//    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private var _stocksLiveData = MutableLiveData<List<StockItem>>()
    val stocksLiveData: LiveData<List<StockItem>> = _stocksLiveData

//    fun insert(stocks: List<StockItem>) {
//        viewModelScope.launch {
//            repository.insert(stocks)
//        }
//    }

//    fun remove(stockSymbol: String) {
//         viewModelScope.launch {
//            repository.remove(stockSymbol)
//        }
//    }

    fun getAllLikedStocks(): LiveData<List<StockItem>> {
        return repository.getAllLikedStocks().asLiveData()
    }

    fun update(isLiked: Boolean, symbol: String) {
        viewModelScope.launch {
            repository.update(isLiked, symbol)
        }
    }

//    init {
//        viewModelScope.launch {
//            _stocksLiveData.postValue(repository.getAllLikedStocks().value)
//        }
//    }
}