package ru.practicum.android.diploma.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.db.entity.FavoriteVacancyEntity

@Dao
interface FavoriteVacancyDao {

    @Insert(entity = FavoriteVacancyDao::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancyToFavoriteList(vacancy: FavoriteVacancyEntity)

    @Delete(entity = FavoriteVacancyDao::class)
    suspend fun deleteVacancyFromFavoriteList(vacancy: FavoriteVacancyEntity)

    // вернуть сортировку когда у нас будут готовы модели Vacancy
  //@Query("SELECT * FROM favourite_vacancies ORDER BY timestamp DESC ")
    @Query("SELECT * FROM favourite_vacancies")
    suspend fun getAllFavouriteVacancies(): List<FavoriteVacancyEntity>

    @Query("SELECT * FROM favourite_vacancies WHERE id = :id")
    suspend fun getFavouriteVacancyById(id: Int): FavoriteVacancyEntity?

    @Query("SELECT COUNT(*) FROM favourite_vacancies WHERE id = :id")
    suspend fun doesVacancyInFavoriteList(id: Int): Boolean


}