package uz.softler.stockapp.data.repository

import kotlinx.coroutines.flow.Flow
import uz.softler.stockapp.data.entities.News
import uz.softler.stockapp.data.local.NewsDao
import uz.softler.stockapp.data.remote.JsonPlaceHolder
import uz.softler.stockapp.utils.DataWrapper
import javax.inject.Inject

class NewsRepository @Inject constructor(
        private val remoteDataSource: JsonPlaceHolder,
        private val localDataSource: NewsDao
) {
    suspend fun insert(newsList: List<News>) {
        localDataSource.insert(newsList)
    }

    fun getNewsLocal(): Flow<List<News>> {
        return localDataSource.getAllNews()
    }

    suspend fun getNewsRemote(): DataWrapper<List<News>> {
        return try {
            DataWrapper.Success(remoteDataSource.getNews())
        } catch (e: Exception) {
            DataWrapper.Error(e.message.toString())
        }
    }
}