package uz.softler.stockapp.ui.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.softler.stockapp.data.entities.News
import uz.softler.stockapp.data.repository.StockRepository
import uz.softler.stockapp.utils.DataWrapper

class NewsViewModel @ViewModelInject constructor(
        private val repository: StockRepository
) : ViewModel() {

    private var _newsLiveData = MutableLiveData<List<News>>()
    val newsLiveData: LiveData<List<News>> = _newsLiveData

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            _isLoading.postValue(true)
            repository.getNews().also {
                when(it) {
                    is DataWrapper.Success -> {
                        _newsLiveData.postValue(it.data)
                    }
                    is DataWrapper.Error -> {
                        Log.d("Error", "Error: ${it.errorMessage}")
                    }
                }
            }
            _isLoading.postValue(false)
        }

    }
}