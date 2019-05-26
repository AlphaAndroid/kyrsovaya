package com.alphan.mainactivity.core

import com.alphan.mainactivity.modules.MainModule
import com.alphan.mainactivity.ui.mainActivity.MainActivity
import com.alphan.mainactivity.ui.mainActivity.MainActivityPresenter
import com.alphan.mainactivity.ui.mainScreen.MainScreenFragment
import com.alphan.mainactivity.ui.mainScreen.MainScreenPresenter
import dagger.Component
import javax.inject.Scope

@Scope
annotation class AppScope {

    @AppScope
    @Component(modules = [MainModule::class])
    interface AppComponent {

        fun inject(presenter: MainActivityPresenter)
        fun inject(activity: MainActivity)

        fun inject(fragment: MainScreenFragment)
        fun inject(basePresenter: MainScreenPresenter)
    }
}