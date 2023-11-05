package ru.practicum.android.diploma.db
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.practicum.android.diploma.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.util.mappers.ConverterListOfStringsDB

@Database(version = 1, entities = [FavoriteVacancyEntity::class])
@TypeConverters(ConverterListOfStringsDB::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favoriteVacancyDao(): FavoriteVacancyDao
}
