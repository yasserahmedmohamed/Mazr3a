package com.yasser.quran.data.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import java.util.*

private const val USER_LOGGED = "userLoginStatus"

class PreferenceProvider(context: Context) {

    private val appContext = context.applicationContext
    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)


    fun ChangeUserLoggedStatus(status: Boolean) {

        SaveValue(USER_LOGGED, status.toString())

    }

    fun GetUserLoggedStatu(): Boolean {

        val stat = GetValue(USER_LOGGED)
        if (stat.isEmpty())
            return false
        else{
            return stat.toBoolean()
        }

    }


    private fun SaveValue(key: String, value: String) {

        preference.edit().putString(
            key, value
        ).apply()
    }

    private fun GetValue(key: String): String {
        val str = preference.getString(key, "")

        return str!!
    }

}