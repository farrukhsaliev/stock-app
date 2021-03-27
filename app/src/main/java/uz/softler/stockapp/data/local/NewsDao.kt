package uz.softler.stockapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.softler.stockapp.data.entities.News

@Dao
interface NewsDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: List<News>)

    @Query("SELECT * FROM news_table")
    fun getAllNews(): Flow<List<News>>
}