package uz.softler.stockapp.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.softler.stockapp.data.local.AppDatabase
import uz.softler.stockapp.data.local.NewsDao
import uz.softler.stockapp.data.local.StockDao
import uz.softler.stockapp.data.remote.JsonPlaceHolder
import uz.softler.stockapp.data.repository.NewsRepository
import uz.softler.stockapp.data.repository.StockRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
            .baseUrl("https://finnhub.io/api/v1/")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(setInterceptor())
            .build()

    @Provides
    fun setInterceptor() = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    fun jsonPlaceHolder(retrofit: Retrofit): JsonPlaceHolder =
            retrofit.create(JsonPlaceHolder::class.java)

    @Singleton
    @Provides
    fun provideDatabase(
            @ApplicationContext context: Context
    ) = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "stocks_database"
    ).fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideStockDao(db: AppDatabase) = db.stockDao()

    @Singleton
    @Provides
    fun provideStockRepository(
            remoteDataSource: JsonPlaceHolder,
            localDataSource: StockDao
    ) =
            StockRepository(remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideNewsDao(db: AppDatabase) = db.newsDao()

    @Singleton
    @Provides
    fun provideNewsRepository(
            remoteDataSource: JsonPlaceHolder,
            localDataSource: NewsDao
    ) =
            NewsRepository(remoteDataSource, localDataSource)

}