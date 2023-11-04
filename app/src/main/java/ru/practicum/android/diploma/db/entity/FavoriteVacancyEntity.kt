package ru.practicum.android.diploma.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="favorite_vacancy")
data class FavoriteVacancyEntity(
    @PrimaryKey
    val id: Int,
    val vacancyName: String,
    val companyName: String,
    @ColumnInfo(name = "logo_url")
    val logoUrl: String?,
    val city: String?,
    val employment: String?,
    val experience: String?,
    @Embedded(prefix = "salary_")
    val salary: Salary?,
    val description: String?,
    @ColumnInfo(name = "key_skills")
    val keySkills: List<String?>,
    @Embedded(prefix = "contacts_")
    val contacts: Contacts?,
    val comment: String?
)

data class Salary(
    val currency: String?,
    val from: Int?,
    val gross: Boolean?,
    val to: Int?
)

data class Contacts(
    val email: String?,
    val name: String?,
    @Embedded(prefix = "phones_")
    val phones: Phone?
)

data class Phone(
    val city: String?,
    val comment: String?,
    val country: String?,
    val number: String?
)
