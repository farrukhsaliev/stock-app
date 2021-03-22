package uz.softler.stockapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.softler.stockapp.data.entities.Stock
import uz.softler.stockapp.data.entities.StockItem
import uz.softler.stockapp.data.entities.StockSymbol

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stockSymbol: StockSymbol)

    @Query("SELECT * FROM stocks_table")
    fun getAllLikedSymbols(): LiveData<List<StockSymbol>>

    @Query("DELETE FROM stocks_table WHERE symbol=:stockSymbol")
    suspend fun remove(stockSymbol: String)

//    @Query("UPDATE stocks_table SET isLiked = :isLiked WHERE id = :id")
//    suspend fun update(isLiked: Boolean, id: Int)

}