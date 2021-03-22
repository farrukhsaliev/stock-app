package uz.softler.stockapp.ui.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.softler.stockapp.data.entities.*
import uz.softler.stockapp.data.repository.StockRepository
import uz.softler.stockapp.utils.DataWrapper
import uz.softler.stockapp.utils.Strings

class MainViewModel @ViewModelInject constructor(
        private val repository: StockRepository
) : ViewModel() {

//    private var _stockLiveData = MutableLiveData<Stocks>()
//    val stockLiveData: LiveData<Stocks> = _stockLiveData

    private var _stocksLiveData = MutableLiveData<List<StockItem>>()
    val stocksLiveData: LiveData<List<StockItem>> = _stocksLiveData

    private var _activeStocksLiveData = MutableLiveData<List<StockItem>>()
    val activeStocksLiveData: LiveData<List<StockItem>> = _activeStocksLiveData

    private var _likedStocksLiveData = MutableLiveData<List<StockItem>>()
    val likedStocksLiveData: LiveData<List<StockItem>> = _likedStocksLiveData

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun insert(stockSymbol: StockSymbol) {
        coroutineScope.launch(Dispatchers.IO) {
            repository.insert(stockSymbol)
        }
    }

//    fun getAllLikedSymbols(): LiveData<List<StockSymbol>> {
//        return repository.getAllLikedSymbols()
//    }

    fun remove(stockSymbol: String) {
        coroutineScope.launch(Dispatchers.IO) {
            repository.remove(stockSymbol)
        }
    }

//    fun update(isLiked: Boolean, id: Int) {
//        coroutineScope.launch(Dispatchers.IO) {
//            repository.update(isLiked, id)
//        }
//    }

    //    fun getStock(ticker: String) = repository.getStock(ticker)
//    fun getStock(): Call<Stock> {
//        return repository.getStock()
//    }

//    fun getStock(ticker: String) : LiveData<Stock> {
//        Log.d("REQUEST", "Send")
//        repository.getStock(ticker).also {
//            when (it) {
//                is DataWrapper.Success -> {
//                    _stockLiveData.postValue(it.data)
//                }
//                is DataWrapper.Error -> {
//                    Log.d("VIEWMODEL", "getSymbols: ${it.errorMessage}")
//                }
//            }
//        }
//        return _stockLiveData
//    }

    init {
//        coroutineScope.launch {
//            repository.getSymbols("https://mboum.com/api/v1/tr/trending?apikey=${Strings.MOBIUM_API_KEY}").also {
//                when (it) {
//                    is DataWrapper.Success -> {
//                        _symbolsLiveData.postValue(it.data)
//                        Log.d("VIEWMODEL", "getSymbols: ${it.data}")
//                    }
//                    is DataWrapper.Error -> {
//                        Log.d("VIEWMODEL", "getSymbols: ${it.errorMessage}")
//                    }
//                }
//            }
//        }

        coroutineScope.launch {
            repository.getStocks("https://mboum.com/api/v1/co/collections/?list=growth_technology_stocks&start=1&apikey=${Strings.MOBIUM_API_KEY}").also {
                when (it) {
                    is DataWrapper.Success -> {
                        _stocksLiveData.postValue(it.data)
                        Log.d("VIEWMODEL", "getSymbols: ${it.data}")
                    }
                    is DataWrapper.Error -> {
                        Log.d("VIEWMODEL", "getSymbols: ${it.errorMessage}")
                    }
                }
            }
        }

        coroutineScope.launch {
            repository.getActiveStocks("https://mboum.com/api/v1/co/collections/?list=undervalued_large_caps&start=1&apikey=${Strings.MOBIUM_API_KEY}").also {
                when (it) {
                    is DataWrapper.Success -> {
                        _activeStocksLiveData.postValue(it.data)
                        Log.d("VIEWMODEL", "getSymbols: ${it.data}")
                    }
                    is DataWrapper.Error -> {
                        Log.d("VIEWMODEL", "getSymbols: ${it.errorMessage}")
                    }
                }
            }
        }

//        coroutineScope.launch {
//            repository.getStocks("https://mboum.com/api/v1/co/collections/?list=growth_technology_stocks&start=1&apikey=${Strings.MOBIUM_API_KEY}").also { dataWrapper ->
//                when (dataWrapper) {
//                    is DataWrapper.Success -> {
//                        val list = ArrayList<StockItem>()
//                        dataWrapper.data.forEach { out ->
//                            stocksLiveData.value?.forEach {
//                                if (out.symbol == it.symbol) {
//                                    list.add(out)
//                                }
//                            }
//                        }
//                        _likedStocksLiveData.postValue(list)
//                        Log.d("VIEWMODEL", "getSymbols: ${dataWrapper.data}")
//                    }
//                    is DataWrapper.Error -> {
//                        Log.d("VIEWMODEL", "getSymbols: ${dataWrapper.errorMessage}")
//                    }
//                }
//            }
//        }

//        coroutineScope.launch {
//            repository.getStock().also {
//                when (it) {
//                    is DataWrapper.Success -> {
//                        _stockLiveData.postValue(it.data)
//                    }
//                    is DataWrapper.Error -> {
//                        Log.e("EXCEPTION", "ViewModel: error occured!")
//                    }
//                }
//            }
//        }
    }

//    fun initializeStocksData() {
//        coroutineScope.launch {
//            _stockLiveData.postValue(repository.getStocksList(_symbolsLiveData.value!!))
//        }
//    }

//    suspend fun getSymbols(): List<Symbol> {
//        var list = ArrayList<Symbol>()
//        coroutineScope.async {
//            repository.getSymbols().also {
//                when (it) {
//                    is DataWrapper.Success -> {
//                        list = it.data as ArrayList<Symbol>
//                        Log.d("VIEWMODEL", "getSymbols: SUCCESS")
//                    }
//                    is DataWrapper.Error -> {
//                        Log.d("VIEWMODEL", "getSymbols: ${it.errorMessage}")
//                    }
//                }
//            }
//        }.await()
//        return list
//    }

//    fun getSymbols(): List<Symbol> {
//        var list = ArrayList<Symbol>()
//        runBlocking {
//            val jobA = async { funA() }
//
//            runBlocking {
//                list = jobA.await() as ArrayList<Symbol>
//            }
//        }
//        return list
//    }
//
//    private suspend fun funA(): List<Symbol> {
//        var list = ArrayList<Symbol>()
//
//        repository.getSymbols().also {
//            when (it) {
//                is DataWrapper.Success -> {
//                    list = it.data as ArrayList<Symbol>
//                    Log.d("VIEWMODEL", "getSymbols: SUCCESS")
//                }
//                is DataWrapper.Error -> {
//                    Log.d("VIEWMODEL", "getSymbols: ${it.errorMessage}")
//                }
//            }
//        }
//        return list
//    }
}