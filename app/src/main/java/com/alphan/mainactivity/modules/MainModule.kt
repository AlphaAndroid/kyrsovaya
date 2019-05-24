package com.alphan.mainactivity.modules

import android.app.Application
import com.alphan.mainactivity.core.AppScope
import dagger.Module
import dagger.Provides

@Module
class MainModule(private val applicationContext: Application) {

    @Provides
    @AppScope
    internal fun providesApplication(): Application {
        return applicationContext
    }
}