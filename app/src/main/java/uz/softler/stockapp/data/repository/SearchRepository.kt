package uz.softler.stockapp.data.repository

import uz.softler.stockapp.data.local.StockDao
import uz.softler.stockapp.data.remote.JsonPlaceHolder
import javax.inject.Inject

class SearchRepository @Inject constructor(
        private val remoteDataSource: JsonPlaceHolder,
        private val localDataSource: StockDao
) {

}