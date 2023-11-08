package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favorite.domain.FavoriteInteractor
import ru.practicum.android.diploma.favorite.domain.impl.FavoriteInteractorImpl
import ru.practicum.android.diploma.filter.domain.impl.AreaControllerImpl
import ru.practicum.android.diploma.filter.domain.impl.IndustriesControllerImpl
import ru.practicum.android.diploma.filter.domain.interfaces.AreaController
import ru.practicum.android.diploma.filter.domain.interfaces.IndustriesController
import ru.practicum.android.diploma.vacancy.domain.impl.VacancyDetailsInteractorImpl
import ru.practicum.android.diploma.vacancy.domain.interactor.VacancyDetailsInteractor
import ru.practicum.android.diploma.search.domain.SearchInteractor
import ru.practicum.android.diploma.search.domain.impl.SearchInteractorImpl
import ru.practicum.android.diploma.similar.domain.SimilarInteractor
import ru.practicum.android.diploma.similar.domain.impl.SimilarInteractorImpl

class InteractorModule {

    val interactorModule = module {

        // Работа с базой данных избранных вакансий
        single<FavoriteInteractor> { FavoriteInteractorImpl(favoriteRepository = get()) }

        //Работа с api: загрузка локаций районов и профессий
        single<AreaController> { AreaControllerImpl(areaRepo = get()) }

        single<IndustriesController> { IndustriesControllerImpl(industryRepo = get()) }

        single<VacancyDetailsInteractor> {
            VacancyDetailsInteractorImpl(repository = get())
        }

        single<SearchInteractor>{
            SearchInteractorImpl(get())
        }

        single<SimilarInteractor>{
            SimilarInteractorImpl(get())
        }

    }

}