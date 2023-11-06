package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favorite.data.impl.FavoriteRepositoryImpl
import ru.practicum.android.diploma.favorite.domain.FavoriteRepository
import ru.practicum.android.diploma.filter.data.impl.AreaRepositoryImpl
import ru.practicum.android.diploma.filter.domain.interfaces.AreaRepository
import ru.practicum.android.diploma.search.data.impl.SearchRepositoryImpl
import ru.practicum.android.diploma.search.domain.SearchRepositry
import ru.practicum.android.diploma.util.mappers.VacancyEntityMapper

class RepositoryModule {

    val repositoryModule = module {

        single<FavoriteRepository> {
            FavoriteRepositoryImpl(
                appDatabase = get(),
                vacancyConvertor = get()
            )
        }

        factory<AreaRepository> { AreaRepositoryImpl(networkClient = get()) }

        factory { VacancyEntityMapper() }

        single<SearchRepositry> {
            SearchRepositoryImpl(networkClient = get(), filtersStorage = get())
        }

    }

}