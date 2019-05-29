package com.alphan.mainactivity.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.alphan.mainactivity.utils.Constants.*

class UserPreferences {

    var shouldShowRadius: Boolean
        get() = sp?.getBoolean(RADIUS_STATUS, false) ?: false
        set(value) {
            editor?.putBoolean(RADIUS_STATUS, value)?.commit()
        }

    var searchRadius: Int
        get() = sp?.getInt(RADIUS, 1000) ?: 1000
        set(value) {
            editor?.putInt(RADIUS, value)?.commit()
        }

    var selectedPlaceType: String
        get() = sp?.getString(SELECTED_PLACE_TYPE, DEFAULT_PLACE_TYPE) ?: DEFAULT_PLACE_TYPE
        set(value) {
            editor?.putString(SELECTED_PLACE_TYPE, value)?.commit()
        }
    var lastUserLat: Float
        get() = sp?.getFloat(LAT, 0.0f) ?: 0.0f
        set(value) {
            editor?.putFloat(LAT, value)?.commit()
        }

    var lastUserLng: Float
        get() = sp?.getFloat(LNG, 0.0f) ?: 0.0f
        set(value) {
            editor?.putFloat(LNG, value)?.commit()
        }

    companion object {

        private var INSTANCE: UserPreferences? = null
        private var sp: SharedPreferences? = null
        private var editor: SharedPreferences.Editor? = null

        @SuppressLint("CommitPrefEdits")
        fun getInstance(context: Application): UserPreferences {
            return if (INSTANCE == null) {
                INSTANCE = UserPreferences()
                sp = context.getSharedPreferences(Constants.USER_PREFERENCES, Context.MODE_PRIVATE)
                editor = sp?.edit()
                INSTANCE as UserPreferences
            } else INSTANCE as UserPreferences
        }
    }
}