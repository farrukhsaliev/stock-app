package uz.softler.stockapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.softler.stockapp.data.entities.ProfileSummary
import uz.softler.stockapp.data.entities.StockItem

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stocks: List<StockItem>)

    @Query("UPDATE stocks_table SET isLiked = :isLiked WHERE symbol = :symbol")
    suspend fun update(isLiked: Boolean, symbol: String)

    @Query("UPDATE stocks_table SET logo = :logo WHERE symbol = :symbol")
    suspend fun updateLogo(logo: String, symbol: String)

    @Query("SELECT * FROM stocks_table WHERE section = :value")
    fun getAllSectionStocks(value: String): Flow<List<StockItem>>

    @Query("SELECT * FROM stocks_table WHERE isLiked = 1")
    fun getAllLikedStocks(): Flow<List<StockItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfileSummary(profileSummary: ProfileSummary)

    @Query("SELECT * FROM profiles_table WHERE symbol = :symbol")
    fun getProfileLocal(symbol: String): Flow<ProfileSummary>
}