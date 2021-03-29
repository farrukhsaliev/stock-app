package uz.softler.stockapp.data.repository

import kotlinx.coroutines.flow.Flow
import uz.softler.stockapp.data.entities.*
import uz.softler.stockapp.data.local.StockDao
import uz.softler.stockapp.data.remote.JsonPlaceHolder
import uz.softler.stockapp.data.entities.Result
import uz.softler.stockapp.utils.DataWrapper
import javax.inject.Inject

class StockRepository @Inject constructor(
    private val remoteDataSource: JsonPlaceHolder,
    private val localDataSource: StockDao
) {

    // Dao

    suspend fun insert(stocks: List<StockItem>) {
        localDataSource.insert(stocks)
    }

    suspend fun update(isLiked: Boolean, symbol: String) {
        localDataSource.update(isLiked, symbol)
    }

    suspend fun updateLogo(logo: String, symbol: String) {
        localDataSource.updateLogo(logo, symbol)
    }

    fun getAllSectionStocks(value: String): Flow<List<StockItem>> {
        return localDataSource.getAllSectionStocks(value)
    }

    fun getProfileLocal(symbol: String): Flow<ProfileSummary> {
        return localDataSource.getProfileLocal(symbol)
    }

    fun getAllLikedStocks(): Flow<List<StockItem>> {
        return localDataSource.getAllLikedStocks()
    }

    // JsonPlaceHolder

    suspend fun getStocks(url: String): DataWrapper<List<StockItem>> {
        return try {
            DataWrapper.Success(remoteDataSource.getStocks(url).quotes)
        } catch (e: Exception) {
            DataWrapper.Error(e.message.toString())
        }
    }

    suspend fun insertProfileSummary(profileSummary: ProfileSummary) {
        localDataSource.insertProfileSummary(profileSummary)
    }

    suspend fun getProfile(url: String): DataWrapper<CompanyProfile> {
        return try {
            DataWrapper.Success(remoteDataSource.getProfile(url))
        } catch (e: Exception) {
            DataWrapper.Error(e.message.toString())
        }
    }

    suspend fun getLookUpStock(symbol: String): DataWrapper<List<Result>> {
        return try {
            DataWrapper.Success(remoteDataSource.getLookUpStock(symbol).result)
        } catch (e: Exception) {
            DataWrapper.Error(e.message.toString())
        }
    }

}