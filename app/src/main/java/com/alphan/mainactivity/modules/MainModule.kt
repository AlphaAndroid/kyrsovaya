package com.alphan.mainactivity.modules

import android.app.Application
import com.alphan.mainactivity.core.AppScope
import com.alphan.mainactivity.utils.UserPreferences
import dagger.Module
import dagger.Provides

@Module
class MainModule(private val applicationContext: Application) {

    @Provides
    @AppScope
    internal fun providesApplication(): Application {
        return applicationContext
    }

    @Provides
    @AppScope
    internal fun providesUserPreferences(context: Application): UserPreferences {
        return UserPreferences.getInstance(context)
    }
}