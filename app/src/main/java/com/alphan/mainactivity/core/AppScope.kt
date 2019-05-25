package com.alphan.mainactivity.core

import com.alphan.mainactivity.modules.MainModule
import dagger.Component
import javax.inject.Scope

@Scope
annotation class AppScope {

    @AppScope
    @Component(modules = [MainModule::class])
    interface AppComponent {
    }
}