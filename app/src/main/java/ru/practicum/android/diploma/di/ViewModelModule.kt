package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.favorite.presentation.view_model.FavoriteViewModel

class ViewModelModule {

    val viewModelModule = module {

        viewModel {
            FavoriteViewModel(get())
        }
    }

}