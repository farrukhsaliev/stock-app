package uz.softler.stockapp.ui.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.softler.stockapp.data.entities.*
import uz.softler.stockapp.data.repository.StockRepository
import uz.softler.stockapp.utils.DataWrapper
import uz.softler.stockapp.utils.Strings

class PagerItemViewModel @ViewModelInject constructor(
        private val repository: StockRepository
) : ViewModel() {

//    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    var logo = ""

    private var _stocksLiveData = MutableLiveData<List<StockItem>>()
    private val stocksLiveData: LiveData<List<StockItem>> = _stocksLiveData

    private var _logoLiveData = MutableLiveData<String>()
    private val logoLiveData: LiveData<String> = _logoLiveData

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

    fun updateLogo(logo: String, symbol: String) {
        viewModelScope.launch {
            repository.updateLogo(logo, symbol)
        }
    }



    fun getStocksFromDb(value: String): LiveData<List<StockItem>> {
        _isLoading.postValue(false)
        return repository.getAllSectionStocks(value).asLiveData()
    }

    fun getAllLikedStocks(): LiveData<List<StockItem>> {
        return repository.getAllLikedStocks().asLiveData()
    }

    fun getLogo(symbol: String): String {
            repository.getLogo("https://autocomplete.clearbit.com/v1/companies/suggest?query=:$symbol").also {
                    when (it) {
                        is DataWrapper.Success -> {
                            logo = it.data
                            Log.d("THISAPP", "success: ${it.data}")
                        }
                        is DataWrapper.Error -> {
                            Log.d("THISAPP", "error: ${it.errorMessage}")
                        }
                    }
                }
        return logo
    }

    fun getStocksRemote(isSend: Boolean, value: String): LiveData<List<StockItem>> {
        if (!isSend) {
            viewModelScope.launch {
                _isLoading.postValue(true)
                repository.getStocks("https://mboum.com/api/v1/co/collections/?list=$value&start=1&apikey=${Strings.MOBIUM_API_KEY}").also {
                    when (it) {
                        is DataWrapper.Success -> {
                            _stocksLiveData.postValue(it.data)
                            Log.d("THISAPP", "success: ${it.data}")
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

//    val breakingNews: MutableLiveData<DataWrapper<StocksResponse>> = MutableLiveData()
//    var breakingNewsPage = 1
//    var breakingNewsResponse: StocksResponse? = null


//    fun getBreakingNews(isSend: Boolean, value: String) = viewModelScope.launch {
//        val response = repository.getPagingStocks("https://mboum.com/api/v1/co/collections/?list=$value&start=$breakingNewsPage&apikey=${Strings.MOBIUM_API_KEY}\"")
//
//    }

//    fun getStocksPage(isSend: Boolean, value: String): LiveData<PagingData<StockItem>> {
//        return repository.getStocksList("https://mboum.com/api/v1/co/collections/?list=$value&start=$1&apikey=${Strings.MOBIUM_API_KEY}\"")
//    }

//
//    private fun handleStocksListResponse(response: Response<StocksResponse>): DataWrapper<StocksResponse> {
//        if (response.isSuccessful) {
//            response.body()?.let {
//                breakingNewsPage += 25
//                if (breakingNewsResponse == null ){
//                    breakingNewsResponse = it
//                } else {
//                    val oldStocks: ArrayList<StockItem> = breakingNewsResponse!!.quotes as ArrayList<StockItem>
//                    val newStocks = it.quotes as ArrayList<StockItem>
//                    oldStocks.addAll(newStocks)
//                }
//                return DataWrapper.Success(breakingNewsResponse ?: it)
//            }
//        }
//        return DataWrapper.Error(response.message())
//    }
}