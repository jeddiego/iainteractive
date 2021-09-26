package mx.com.ia.cinemorelia.service

import android.content.Context
import android.content.SharedPreferences

class CinemoreliaSharedPreference(
    context: Context
) {
    private val PREFS_NAME = "CinemoreliaShP"
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveValue(KEY_NAME: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, text)
        editor.apply()
    }

    fun getStringValue(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }
}