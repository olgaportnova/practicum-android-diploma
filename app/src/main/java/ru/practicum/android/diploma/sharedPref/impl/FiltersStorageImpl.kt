package ru.practicum.android.diploma.sharedPref.impl

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.filter.data.impl.dto.ParamsFilterModelDto
import ru.practicum.android.diploma.sharedPref.FiltersStorage

class FiltersStorageImpl(
    private val sharedPref: SharedPreferences,
    private val gson: Gson): FiltersStorage {

    companion object {
        const val KEY_SAVED_PARAMS_FILTER = "key_saved_params"

        //Данный ключ для сохранения статуса, наличия, отсутствия параметров в хранилище
        //В дальнейшем может быть использован в функции setStatusParamsFilter
        const val KEY_STATUS_PARAMS_FILTER = "key_status_params"
    }

    /**
     * Simple function for get saved filters from storage.
     * @return ParamsFilterModelDto
     */
    override fun getParamsFiltes(): ParamsFilterModelDto {
        val jsonSavedParams = sharedPref.getString(KEY_SAVED_PARAMS_FILTER,null)
        val itemType = object : TypeToken<ParamsFilterModelDto>(){}.type

        return gson.fromJson(jsonSavedParams,itemType)
    }

    /**
     * Simple function for get save status filters from storage.
     * @return Boolean
     */
    override fun getStateSavedFilters(): Boolean {
        return sharedPref.getBoolean(KEY_STATUS_PARAMS_FILTER,false)
    }
}