package uz.softler.stockapp.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import uz.softler.stockapp.data.entities.StockItem
import uz.softler.stockapp.data.repository.StockRepository

class FavouritesViewModel @ViewModelInject constructor(
        private val repository: StockRepository
) : ViewModel() {

    fun getAllLikedStocks(): LiveData<List<StockItem>> {
        return repository.getAllLikedStocks().asLiveData()
    }

    fun update(isLiked: Boolean, symbol: String) {
        viewModelScope.launch {
            repository.update(isLiked, symbol)
        }
    }
}