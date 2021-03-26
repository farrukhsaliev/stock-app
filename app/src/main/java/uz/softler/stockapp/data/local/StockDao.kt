package uz.softler.stockapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import uz.softler.stockapp.data.entities.StockItem

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stocks: List<StockItem>)

//    @Query("DELETE FROM stocks_table WHERE symbol=:stockSymbol")
//    suspend fun remove(stockSymbol: String)

    @Query("UPDATE stocks_table SET isLiked = :isLiked WHERE symbol = :symbol")
    suspend fun update(isLiked: Boolean, symbol: String)

    @Query("SELECT * FROM stocks_table WHERE isLiked = 1")
    fun getAllLikedStocks(): LiveData<List<StockItem>>

}