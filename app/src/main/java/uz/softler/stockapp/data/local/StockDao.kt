package uz.softler.stockapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import uz.softler.stockapp.data.entities.StockItem

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stockItem: StockItem)

    @Query("SELECT * FROM stocks_table")
    fun getAllLikedStocks(): LiveData<List<StockItem>>

    @Query("SELECT * FROM stocks_table")
    fun getAllLikedStocksList(): List<StockItem>

    @Query("DELETE FROM stocks_table WHERE symbol=:stockSymbol")
    suspend fun remove(stockSymbol: String)

    @Query("UPDATE stocks_table SET isLiked = :isLiked WHERE symbol = :symbol")
    suspend fun update(isLiked: Boolean, symbol: String)

//    @Update
//    suspend fun update(isLiked: Boolean, symbol: String)

}