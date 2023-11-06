package ru.practicum.android.diploma.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.db.AppDatabase
import ru.practicum.android.diploma.hhApi.ApiHH
import ru.practicum.android.diploma.hhApi.NetworkClient
import ru.practicum.android.diploma.hhApi.impl.NetworkClientImpl
import ru.practicum.android.diploma.sharedPref.impl.FiltersStorageImpl

const val DATABASE_NAME = "favorite_vacancy"

class DataModule {

    val dataModule = module {
        single {
            Room.databaseBuilder(
                androidContext(),
                AppDatabase::class.java,
                DATABASE_NAME
            ).build()
        }

        single { get<AppDatabase>().favoriteVacancyDao() }

        single<SharedPreferences> {
            androidContext().getSharedPreferences(
                FiltersStorageImpl.KEY_SAVED_PARAMS_FILTER,
                Context.MODE_PRIVATE
            )
        }

        single<ApiHH> {
            val baseUrl = "https://api.hh.ru/"

            val interceptorHttp = HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptorHttp)
                .build()

            // retrofit initialisation will come with class member initialisation
            Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiHH::class.java)
        }

        single<NetworkClient>{ NetworkClientImpl(hhApi = get(), context = get()) }

        single { Gson() }


    }

}