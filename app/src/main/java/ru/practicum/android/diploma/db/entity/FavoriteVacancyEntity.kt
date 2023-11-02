package ru.practicum.android.diploma.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="favorite_vacancy")
data class FavoriteVacancyEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
)