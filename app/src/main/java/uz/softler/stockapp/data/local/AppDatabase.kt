package uz.softler.stockapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.softler.stockapp.data.entities.News
import uz.softler.stockapp.data.entities.StockItem

@Database(entities = [StockItem::class, News::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stockDao(): StockDao
    abstract fun newsDao(): NewsDao
}