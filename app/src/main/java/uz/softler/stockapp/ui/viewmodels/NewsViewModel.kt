package uz.softler.stockapp.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.softler.stockapp.data.entities.News
import uz.softler.stockapp.data.repository.NewsRepository
import uz.softler.stockapp.utils.DataWrapper

class NewsViewModel @ViewModelInject constructor(
        private val repository: NewsRepository
) : ViewModel() {

    private var _newsLiveData = MutableLiveData<List<News>>()
    val newsLiveData: LiveData<List<News>> = _newsLiveData

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun insert(newsList: List<News>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(newsList)
        }
    }

    fun getNewsLocal(): LiveData<List<News>> {
        _isLoading.postValue(false)
        return repository.getNewsLocal().asLiveData()
    }

    init {
        viewModelScope.launch {
            _isLoading.postValue(true)
            repository.getNewsRemote().also {
                when (it) {
                    is DataWrapper.Success -> {
                        _newsLiveData.postValue(it.data)
                    }
                    is DataWrapper.Error -> {

                    }
                }
            }
            _isLoading.postValue(false)
        }
    }
}