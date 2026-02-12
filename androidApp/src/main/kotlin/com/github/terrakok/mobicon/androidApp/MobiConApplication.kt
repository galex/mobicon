package com.github.terrakok.mobicon.androidApp

import android.app.Application
import com.github.terrakok.mobicon.initKoin

class MobiConApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
