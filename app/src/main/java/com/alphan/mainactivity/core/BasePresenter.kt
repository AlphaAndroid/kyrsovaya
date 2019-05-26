package com.alphan.mainactivity.core

import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import javax.inject.Inject

open class BasePresenter<View : MvpView> : MvpPresenter<View>() {

    @Inject
    lateinit var appContext: Application

    /**
     * Возвращает true, если все права выданы, false если нет.
     */
    protected fun hasPermission(permissions: Array<String>): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            for (permission in permissions)
                if (appContext.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
                    return false
        return true
    }
}