package com.ngangavic.test.sharedprefs

import android.content.Context
import android.content.SharedPreferences


class LocalStorage(context: Context) {
    val PREFS_FILENAME = "login_prefs"
    val PREFS_FILENAME2 = "data_prefs"
    val sharedPrefData: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)
    val sharedPrefData2: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME2, 0)

    fun save(KEY_NAME: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPrefData.edit()

        editor.putString(KEY_NAME, value)

        editor.apply()
    }

    fun save2(KEY_NAME: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPrefData2.edit()

        editor.putString(KEY_NAME, value)

        editor.apply()
    }

    fun getValueString(KEY_NAME: String): String? {

        return sharedPrefData.getString(KEY_NAME, null)
    }

    fun clearSharedPreference() {

        val editor: SharedPreferences.Editor = sharedPrefData.edit()

        //sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        editor.clear()
        editor.apply()
    }

    fun removeValue(KEY_NAME: String) {

        val editor: SharedPreferences.Editor = sharedPrefData.edit()

        editor.remove(KEY_NAME)
        editor.apply()
    }

}