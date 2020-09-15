package me.jorgecasariego.pororopeliculas.base

import android.app.Application
import me.jorgecasariego.pororopeliculas.injection.Modules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(Modules.all)
        }
    }



}