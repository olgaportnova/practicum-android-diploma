package ru.practicum.android.diploma.sharedPref.impl

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.filter.data.impl.dto.ParamsFilterModelDto

class FiltersStorageImpl(
    private val sharedPref: SharedPreferences,
    private val gson: Gson): FiltersStorage {

    override fun getParamsFiltes(): ParamsFilterModelDto {
        TODO("Not yet implemented")
    }

    override fun getStateSavedFilters(): Boolean {
        TODO("Not yet implemented")
    }
}