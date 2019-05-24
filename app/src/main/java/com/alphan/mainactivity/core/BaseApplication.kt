package com.alphan.mainactivity.core

import androidx.multidex.MultiDexApplication
import com.alphan.mainactivity.modules.MainModule
import com.squareup.leakcanary.LeakCanary

class BaseApplication : MultiDexApplication() {

    companion object {
        lateinit var appComponent: AppScope.AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) return // вешаю канарейку для отслеживания ликов
        LeakCanary.install(this)
        appComponent = DaggerAppScope_AppComponent.builder().mainModule(MainModule(this)).build()
    }
}