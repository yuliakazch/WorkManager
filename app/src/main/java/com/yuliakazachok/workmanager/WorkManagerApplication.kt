package com.yuliakazachok.workmanager

import android.app.Application
import com.yuliakazachok.workmanager.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class WorkManagerApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@WorkManagerApplication)
            modules(appModule)
        }
    }
}