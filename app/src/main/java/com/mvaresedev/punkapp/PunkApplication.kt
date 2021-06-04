package com.mvaresedev.punkapp

import android.app.Application
import com.mvaresedev.punkapp.di.appModule
import com.mvaresedev.punkapp.di.dataModule
import com.mvaresedev.punkapp.di.uiModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
class PunkApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@PunkApplication)
            modules(listOf(appModule, dataModule, uiModule))
        }
    }
}