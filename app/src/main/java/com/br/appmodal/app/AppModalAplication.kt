package com.br.appmodal.app

import android.app.Application
import com.br.appmodal.di.*
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppModalAplication: Application() {

    override fun onCreate(){
        super.onCreate()

        Stetho.initializeWithDefaults(this.applicationContext)

        // start Koin!
        startKoin {
            // Android context
            androidContext(this@AppModalAplication)
            // modules
            modules(arrayListOf(
                appModule,
                viewModelModule,
                networkModule,
                databaseModule,
                daoModule
            ))
        }
    }
}