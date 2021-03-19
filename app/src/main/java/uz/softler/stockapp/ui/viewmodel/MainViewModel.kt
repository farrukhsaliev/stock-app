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
import uz.softler.stockapp.data.entities.Stock
import uz.softler.stockapp.data.entities.Symbol
import uz.softler.stockapp.data.repository.StockRepository
import uz.softler.stockapp.utils.DataWrapper


class MainViewModel @ViewModelInject constructor(
    private val repository: StockRepository
) : ViewModel() {

    private var _stockLiveData = MutableLiveData<Stock>()
    val stockLiveData: LiveData<Stock> = _stockLiveData

    private var _symbolsLiveData = MutableLiveData<List<Symbol>>()
    val symbolsLiveData: LiveData<List<Symbol>> = _symbolsLiveData

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun insert(stock: Stock) {
        coroutineScope.launch(Dispatchers.IO) {
            repository.insert(stock)
        }
    }

    fun getAllLikedStocks(): LiveData<List<Stock>> {
        return repository.getAllLikedStocks()
    }

    fun unlikeStock(ticker: String) {
        coroutineScope.launch(Dispatchers.IO) {
            repository.unlikeStock(ticker)
        }
    }

    //    fun getStock(ticker: String) = repository.getStock(ticker)
//    fun getStock(): Call<Stock> {
//        return repository.getStock()
//    }

//    fun getStock() : LiveData<Stock> {
//        Log.d("REQUEST", "Send")
////        _stockLiveData = repository.getStock()
//        return _stockLiveData
//    }

    init {
        coroutineScope.launch {
            repository.getStock().also {
                when (it) {
                    is DataWrapper.Success -> {
                        _stockLiveData.postValue(it.data)
                    }
                    is DataWrapper.Error -> {
                        Log.e("EXCEPTION", "ViewModel: error occured!")
                    }
                }
            }
        }

        coroutineScope.launch {
            repository.getSymbols().also {
                when (it) {
                    is DataWrapper.Success -> {
                        _symbolsLiveData.postValue(it.data)
                    }
                    is DataWrapper.Error -> {
                        Log.d("ERROR", "getSymbols: ${it.errorMessage}")
                    }
                }
            }
        }
    }

    fun initializeStocksData() {
        //
    }

//    suspend fun getSymbols(): List<Symbol> {
//        var list = ArrayList<Symbol>()
//        coroutineScope.async {
//            repository.getSymbols().also {
//                when (it) {
//                    is DataWrapper.Success -> {
//                        list = it.data as ArrayList<Symbol>
//                        Log.d("ERROR", "getSymbols: SUCCESS")
//                    }
//                    is DataWrapper.Error -> {
//                        Log.d("ERROR", "getSymbols: ${it.errorMessage}")
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
//                    Log.d("ERROR", "getSymbols: SUCCESS")
//                }
//                is DataWrapper.Error -> {
//                    Log.d("ERROR", "getSymbols: ${it.errorMessage}")
//                }
//            }
//        }
//        return list
//    }
}