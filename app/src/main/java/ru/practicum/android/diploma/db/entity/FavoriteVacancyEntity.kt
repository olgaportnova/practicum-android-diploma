package ru.practicum.android.diploma.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="favorite_vacancy")
data class FavoriteVacancyEntity(
    //TODO: сделать entity как будет модель Вакансии

    @PrimaryKey
    val id: Int,
    val name: String,
)