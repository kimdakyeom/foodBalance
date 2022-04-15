package com.dkkim.anew.Util

import android.content.Context
import android.content.SharedPreferences

object MySharedPreferences {
    private val MY_AUTHORIZATION: String = "authorization"

    fun setEmail(
        context: Context,
        email: String,
        pwd:String
    ) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_AUTHORIZATION, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("email", email)
        editor.putString("pwd",pwd)
        editor.apply()
    }

    fun setFcmToken(context: Context, fcmToken: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_AUTHORIZATION, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("fcmToken", fcmToken)
        editor.apply()
    }
    fun setDate(context: Context, date:String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_AUTHORIZATION, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("date", date.toString())
        editor.apply()
    }


    fun getDate(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_AUTHORIZATION, Context.MODE_PRIVATE)
        return prefs.getString("date", "").toString()
    }
    fun getFcmToken(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_AUTHORIZATION, Context.MODE_PRIVATE)
        return prefs.getString("fcmToken", "").toString()
    }


    fun getEmail(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_AUTHORIZATION, Context.MODE_PRIVATE)
        return prefs.getString("email", "").toString()
    }
    fun getPwd(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_AUTHORIZATION, Context.MODE_PRIVATE)
        return prefs.getString("pwd", "").toString()
    }


    fun clearUser(context: Context) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_AUTHORIZATION, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

}