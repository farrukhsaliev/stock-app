package uz.softler.stockapp.utils

import android.content.Context
import android.content.SharedPreferences

class MyPreferences(context: Context) {
    private val preferences: SharedPreferences =
            context.getSharedPreferences(Strings.LANGUAGE_KEY, Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = preferences.edit()

    fun setLang(lang: String) {
        editor.putString(Strings.LANGUAGE_KEY, lang)
        editor.apply()
    }

    fun getLang() = preferences.getString(Strings.LANGUAGE_KEY, "NOT")

}