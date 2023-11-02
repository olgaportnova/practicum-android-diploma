package ru.practicum.android.diploma.db.entity

import androidx.room.Entity


@Entity(tableName="favourite_vacancies")
data class FavoriteVacancyEntity(
    val id: Int,
    val name: String,
)