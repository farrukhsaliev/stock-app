package uz.softler.stockapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.softler.stockapp.data.entities.Stock

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stock: Stock)

    @Query("SELECT * FROM stocks_table")
    fun getAllLikedStocks(): Flow<List<Stock>>

    @Query("DELETE FROM stocks_table WHERE ticker=:ticker")
    suspend fun unlikeStock(ticker: String)
}