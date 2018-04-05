package com.exceptionteam17.a101sexchallenges.helpers

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class ShPrefs {
    companion object {
        private const val isFirst : String = "isFirstRunOn101SexChallenges"
        private const val age : String = "isAgeCheckedOn101SexChallenges"
        private const val progress : String = "progress101SexChallenges"


        fun isFirstRun (context: Context) : Boolean{
            val prefs : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getBoolean(isFirst, true)
        }

        fun setIsFirstRun (context: Context, isIt : Boolean){
            val prefs : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = prefs.edit()
            editor.putBoolean(isFirst, isIt)
            editor.apply()
        }

        fun isAgeChecked (context: Context) : Boolean{
            val prefs : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getBoolean(age, true)
        }

        fun ageComfirmed (context: Context, isIt : Boolean){
            val prefs : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = prefs.edit()
            editor.putBoolean(age, isIt)
            editor.apply()
        }

        fun getProgress (context: Context) : Int{
            val prefs : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getInt(progress, 0)
        }

        fun addToProgress (context: Context){
            val prefs : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = prefs.edit()
            editor.putInt(progress, (getProgress(context) + 1))
            editor.apply()
        }
    }
}