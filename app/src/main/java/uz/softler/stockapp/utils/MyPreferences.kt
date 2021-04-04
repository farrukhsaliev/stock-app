package uz.softler.stockapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class MyPreferences(val context: Context) {

    private val preferences: SharedPreferences =
            context.getSharedPreferences(Strings.LANGUAGE_KEY, Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = preferences.edit()

    fun setLang(lang: String) {
        editor.putString(Strings.LANGUAGE_KEY, lang)
        editor.apply()
    }

    fun changeDarkMode(key: String) {
        editor.putString(Strings.DARK_MODE_KEY, key)
        editor.apply()
    }

    fun getLang() = preferences.getString(Strings.LANGUAGE_KEY, "NOT")

    fun getDarkModeStatus() = preferences.getString(Strings.DARK_MODE_KEY, "NOT")

    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}