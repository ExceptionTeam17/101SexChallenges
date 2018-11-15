package com.exceptionteam17.a101sexchallenges.helpers

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class ShPrefs {
    companion object {
        private const val isFirst : String = "isFirstRunOn101SexChallenges"
        private const val age : String = "isAgeCheckedOn101SexChallenges"
        private const val progress : String = "progress101SexChallenges"
        private const val interstitial : String = "interstitial101SexChallenges"
        private const val finish : String = "interstitial101SexChallengesFinish"


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

        fun isFinishing (context: Context) : Boolean{
            val prefs : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getBoolean(finish, false)
        }

        fun setFinish (context: Context, isIt: Boolean){
            val prefs : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = prefs.edit()
            editor.putBoolean(finish, isIt)
            editor.apply()
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

        fun getAdd (context: Context) : Int{
            val prefs : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getInt(interstitial, 0)
        }

        fun addToAdd (context: Context, count: Int){
            val prefs : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = prefs.edit()
            editor.putInt(interstitial, count)
            editor.apply()
        }
    }
}