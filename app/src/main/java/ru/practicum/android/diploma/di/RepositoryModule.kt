package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favorite.data.impl.FavoriteRepositoryImpl
import ru.practicum.android.diploma.favorite.domain.FavoriteRepository
import ru.practicum.android.diploma.filter.data.repository.AreaRepositoryImpl
import ru.practicum.android.diploma.filter.data.repository.IndustryRepositoryImpl
import ru.practicum.android.diploma.filter.domain.interfaces.AreaRepository
import ru.practicum.android.diploma.filter.domain.interfaces.IndustryRepository
import ru.practicum.android.diploma.util.mappers.VacancyEntityMapper
import ru.practicum.android.diploma.vacancy.data.impl.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyDetailsRepository

class RepositoryModule {

    val repositoryModule = module {

        single<FavoriteRepository> {
            FavoriteRepositoryImpl(
                appDatabase = get(),
                vacancyConvertor = get()
            )
        }

        factory<AreaRepository> { AreaRepositoryImpl(networkClient = get()) }

        factory<IndustryRepository> { IndustryRepositoryImpl(networkClient = get()) }

        factory { VacancyEntityMapper() }

        single<VacancyDetailsRepository> {
            VacancyDetailsRepositoryImpl(
                networkClient = get(),
                vacancyMapper = get(),
                context = get(),
            )
        }

    }

}