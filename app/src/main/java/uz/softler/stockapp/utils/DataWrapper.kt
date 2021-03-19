package uz.softler.stockapp.utils

sealed class DataWrapper<T : Any> {
    data class Success<T : Any>(val data: T) : DataWrapper<T>()
    data class Error<T : Any>(val errorMessage: String) : DataWrapper<T>()
}
