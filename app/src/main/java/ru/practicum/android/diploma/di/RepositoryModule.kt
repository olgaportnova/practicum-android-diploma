package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favorite.data.impl.FavoriteRepositoryImpl
import ru.practicum.android.diploma.favorite.domain.FavoriteRepository

class RepositoryModule {

    val repositoryModule = module {

        single<FavoriteRepository> {
            FavoriteRepositoryImpl(get(), get())
        }


    }

}