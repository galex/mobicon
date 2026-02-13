package com.github.terrakok.mobicon.androidApp

import android.app.Application
import com.github.terrakok.mobicon.initKoin
import org.koin.android.ext.koin.androidContext

class MobiConApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MobiConApplication)
        }
    }
}
