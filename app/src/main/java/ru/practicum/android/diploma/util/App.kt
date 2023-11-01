package ru.practicum.android.diploma.util

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import ru.practicum.android.diploma.di.DataModule
import ru.practicum.android.diploma.di.InteractorModule
import ru.practicum.android.diploma.di.RepositoryModule
import ru.practicum.android.diploma.di.ViewModelModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                DataModule().dataModule,
                RepositoryModule().repositoryModule,
                InteractorModule().interactorModule,
                ViewModelModule().viewModelModule
            )
        }
    }
}