package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.db.AppDatabase

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

    }

}