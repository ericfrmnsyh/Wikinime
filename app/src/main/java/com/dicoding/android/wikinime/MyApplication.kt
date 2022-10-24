package com.dicoding.android.wikinime

import android.app.Application
import com.dicoding.android.wikinime.di.useCaseModule
import com.dicoding.android.wikinime.di.viewModelModule
import com.dicoding.android.wikinime.core.di.databaseModule
import com.dicoding.android.wikinime.core.di.networkModule
import com.dicoding.android.wikinime.core.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                )
            )
        }
    }
}