package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.favorite.presentation.view_model.FavoriteViewModel
import ru.practicum.android.diploma.filter.presentation.view_model.CountryVm
import ru.practicum.android.diploma.filter.presentation.view_model.DistrictVm
import ru.practicum.android.diploma.filter.presentation.view_model.FiltersVm
import ru.practicum.android.diploma.filter.presentation.view_model.IndustryVm
import ru.practicum.android.diploma.filter.presentation.view_model.WorkPlaceVm
import ru.practicum.android.diploma.search.presentation.view_model.SearchViewModel
import ru.practicum.android.diploma.similar.presentation.view_model.SimilarViewModel
import ru.practicum.android.diploma.vacancy.presentation.view_model.VacancyDetailsViewModel

class ViewModelModule {

    val viewModelModule = module {
        viewModel { FavoriteViewModel(favoriteInteractor = get()) }

        viewModel { DistrictVm(areaController = get()) }

        viewModel { CountryVm(areaController = get()) }

        viewModel { IndustryVm(industriesController = get()) }

        viewModel { WorkPlaceVm(filtersController = get()) }

        viewModel { FiltersVm(filtersController = get()) }

        viewModel {
            SearchViewModel(searchInteractor = get())
        }

        viewModel {
            VacancyDetailsViewModel(application = get(), vacancyDetailsInteractor = get())
        }

        viewModel {
            SimilarViewModel(similarInteractor = get())
        }
    }

}