package ru.practicum.android.diploma.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.db.entity.FavoriteVacancyEntity

@Database(version = 1, entities = [FavoriteVacancyEntity::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteVacancyDao(): FavoriteVacancyDao
}

