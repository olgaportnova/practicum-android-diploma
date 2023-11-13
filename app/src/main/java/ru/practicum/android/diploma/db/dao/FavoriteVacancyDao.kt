package ru.practicum.android.diploma.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.db.entity.FavoriteVacancyEntity

@Dao
interface FavoriteVacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancyToFavoriteList(vacancy: FavoriteVacancyEntity)

    @Delete
    suspend fun deleteVacancyFromFavoriteList(vacancy: FavoriteVacancyEntity)

    @Query("SELECT * FROM favorite_vacancy")
    suspend fun getAllFavouriteVacancies(): List<FavoriteVacancyEntity>

    @Query("SELECT * FROM favorite_vacancy WHERE id = :id")
    suspend fun getFavouriteVacancyById(id: Int): FavoriteVacancyEntity?

    @Query("SELECT COUNT(*) FROM favorite_vacancy WHERE id = :id")
    suspend fun doesVacancyInFavoriteList(id: Int): Boolean
}