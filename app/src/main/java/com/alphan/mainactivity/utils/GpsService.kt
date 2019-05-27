package com.alphan.mainactivity.utils

import android.annotation.SuppressLint
import android.app.Application
import com.google.android.gms.location.*

/**
 * простой gps сервис для отслеживания изменений координат юзера. вынесен в отдельный класс для того, чтобы
 * избежать утечки памяти. т.к. LocationCallback это анонимный класс.
 * Так же это решение просто красиво выглядит и удобно
 */
class GpsService : LocationCallback() {

    private var client: FusedLocationProviderClient? = null
    private var callback: LocationUpdateCallback? = null

    private val locationRequest: LocationRequest
        get() = LocationRequest.create()
                .setInterval(5000)
                .setFastestInterval(0)
                .setMaxWaitTime(0)
                .setSmallestDisplacement(0f)
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)

    override fun onLocationResult(locationResult: LocationResult) {
        super.onLocationResult(locationResult)
        callback?.onLocationResult(locationResult)
    }

    @SuppressLint("MissingPermission")
    fun start(context: Application, callback: LocationUpdateCallback) {
        this.callback = callback
        client = LocationServices.getFusedLocationProviderClient(context)
        client?.requestLocationUpdates(locationRequest, this, null)
    }

    fun stop() {
        client?.removeLocationUpdates(this)
        callback = null
        client = null
    }

    interface LocationUpdateCallback {
        fun onLocationResult(locationResult: LocationResult)
    }
}
