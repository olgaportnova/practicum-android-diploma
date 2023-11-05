package ru.practicum.android.diploma.sharedPref.impl

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.filter.data.impl.dto.FilterDto
import ru.practicum.android.diploma.sharedPref.FiltersStorage

class FiltersStorageImpl(
    private val sharedPref: SharedPreferences,
    private val gson: Gson): FiltersStorage {

    companion object {
        const val KEY_SAVED_PARAMS_FILTER = "key_saved_params"
    }

    /**
     * Simple function for get saved filters from storage.
     * @return ParamsFilterModelDto
     */
    override fun getParamsFilters(): FilterDto? {
        val jsonSavedParams = sharedPref.getString(KEY_SAVED_PARAMS_FILTER,null)
        val itemType = object : TypeToken<FilterDto>(){}.type

        if (jsonSavedParams == null || jsonSavedParams == "") {
            return null
        }
        return gson.fromJson(jsonSavedParams,itemType)
    }

    /**
     * Simple function for add filters in storage.
     */
    override fun addParamsFilters(params:FilterDto) {
         sharedPref.edit()
             .putString(KEY_SAVED_PARAMS_FILTER,gson.toJson(params))
             .apply()
    }
}