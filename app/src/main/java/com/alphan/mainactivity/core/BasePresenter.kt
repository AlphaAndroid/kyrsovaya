package com.alphan.mainactivity.core

import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import com.alphan.mainactivity.utils.Constants.PERMISSION_REQUEST_CODE
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import javax.inject.Inject

open class BasePresenter<View : MvpView> : MvpPresenter<View>() {

    @Inject
    lateinit var appContext: Application

    /**
     * Метод анализирующий запрос на права. Т.е. после запроса на права в onRequestPermissionsResult приходит ответ. Данный метод возвращает false
     * если все права получены и true если хотябы одно из прав не было получено.
     */
    fun analyzePermissionRequest(requestCode: Int, grantResults: IntArray, amount: Int): Boolean {
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.size == amount)
            for (`val` in grantResults)
                if (`val` == PackageManager.PERMISSION_DENIED) return true
        return false
    }

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