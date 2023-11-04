package ru.practicum.android.diploma.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.db.AppDatabase
import ru.practicum.android.diploma.sharedPref.impl.FiltersStorageImpl

const val DATABASE_NAME = "favorite_vacancy"

class DataModule {

    val dataModule = module {

        single {
            Room.databaseBuilder(
                androidContext(),
                AppDatabase::class.java,
                DATABASE_NAME
            )
                .build()
        }

        single { get<AppDatabase>().favoriteVacancyDao() }

        single<SharedPreferences>{
            androidContext().getSharedPreferences(FiltersStorageImpl.KEY_SAVED_PARAMS_FILTER,Context.MODE_PRIVATE)
        }

        single { Gson() }


    }

}