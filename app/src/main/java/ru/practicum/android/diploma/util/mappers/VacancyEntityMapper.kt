package ru.practicum.android.diploma.util.mappers

import ru.practicum.android.diploma.db.entity.ContactsEntity
import ru.practicum.android.diploma.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.db.entity.PhoneEntity
import ru.practicum.android.diploma.db.entity.SalaryEntity
import ru.practicum.android.diploma.search.domain.models.Contacts
import ru.practicum.android.diploma.search.domain.models.Phone
import ru.practicum.android.diploma.search.domain.models.Salary
import ru.practicum.android.diploma.search.domain.models.Vacancy

class VacancyEntityMapper {

    fun vacancyEntityToVacancy(vacancyEntity: FavoriteVacancyEntity): Vacancy {
        return with(vacancyEntity) {
            Vacancy(
                id = id,
                vacancyName = vacancyName,
                companyName = companyName,
                alternateUrl = alternateUrl,
                logoUrl = logoUrl,
                city = city,
                employment = employment,
                experience = experience,
                salary = createSalary(salary),
                description = description,
                keySkills = keySkills,
                contacts = createContacts(contacts),
                comment = comment
            )
        }
    }

    private fun createSalary(salaryEntity: SalaryEntity?): Salary? {
        return salaryEntity?.let {
            Salary(
                currency = it.currency,
                from = it.from,
                gross = it.gross,
                to = it.to
            )
        }
    }

    private fun createContacts(contactsEntity: ContactsEntity?): Contacts? {
        return contactsEntity?.let {
            Contacts(
                email = it.email,
                name = it.name,
                phones = it.phones?.map { createPhone(it) }
            )
        }
    }

    private fun createPhone(phoneEntity: PhoneEntity?): Phone? {
        return phoneEntity?.let {
            Phone(
                city = it.city,
                comment = it.comment,
                country = it.country,
                number = it.number
            )
        }
    }



    fun vacancyVacancyToEntity(vacancy: Vacancy): FavoriteVacancyEntity {
        return with(vacancy) {
            FavoriteVacancyEntity(
                id = id,
                vacancyName = vacancyName,
                companyName = companyName,
                alternateUrl = alternateUrl,
                logoUrl = logoUrl,
                city = city,
                employment = employment,
                experience = experience,
                salary = createSalaryEntity(salary),
                description = description,
                keySkills = keySkills,
                contacts = createContactsEntity(contacts),
                comment = comment
            )
        }
    }

    private fun createSalaryEntity(salary: Salary?): SalaryEntity? {
        return salary?.let {
            SalaryEntity(
                currency = it.currency,
                from = it.from,
                gross = it.gross,
                to = it.to
            )
        }
    }

    private fun createContactsEntity(contacts: Contacts?): ContactsEntity? {
        return contacts?.let {
            ContactsEntity(
                email = it.email,
                name = it.name,
                phones = it.phones?.map { createPhoneEntity(it) }
            )
        }
    }

    private fun createPhoneEntity(phone: Phone?): PhoneEntity? {
        return phone?.let {
            PhoneEntity(
                city = it.city,
                comment = it.comment,
                country = it.country,
                number = it.number
            )
        }
    }

}
