package uz.softler.stockapp.ui.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import uz.softler.stockapp.data.entities.Stock
import uz.softler.stockapp.data.repository.StockRepository
import javax.inject.Inject

class MainViewModel @ViewModelInject constructor(
        private val repository: StockRepository
) : ViewModel() {

    fun insert(stock: Stock) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(stock)
        }
    }

    fun getAllLikedStocks(): LiveData<List<Stock>> {
        return repository.getAllLikedStocks().asLiveData()
    }

    fun unlikeStock(ticker: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.unlikeStock(ticker)
        }
    }

//    fun getStock(ticker: String) = repository.getStock(ticker)

}