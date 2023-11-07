package ru.practicum.android.diploma.util.mappers

import ru.practicum.android.diploma.db.entity.ContactsEntity
import ru.practicum.android.diploma.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.db.entity.PhoneEntity
import ru.practicum.android.diploma.db.entity.SalaryEntity
import ru.practicum.android.diploma.search.domain.models.ContactsTest
import ru.practicum.android.diploma.search.domain.models.PhoneTest
import ru.practicum.android.diploma.search.domain.models.SalaryTest
import ru.practicum.android.diploma.search.domain.models.VacancyForTests

class VacancyEntityMapper {

    fun vacancyEntityToVacancy(vacancyEntity: FavoriteVacancyEntity): VacancyForTests {
        return with(vacancyEntity) {
            VacancyForTests(
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

    private fun createSalary(salaryEntity: SalaryEntity?): SalaryTest? {
        return salaryEntity?.let {
            SalaryTest(
                currency = it.currency,
                from = it.from,
                gross = it.gross,
                to = it.to
            )
        }
    }

    private fun createContacts(contactsEntity: ContactsEntity?): ContactsTest? {
        return contactsEntity?.let {
            ContactsTest(
                email = it.email,
                name = it.name,
                phones = it.phones?.map { createPhone(it) }
            )
        }
    }

    private fun createPhone(phoneEntity: PhoneEntity?): PhoneTest? {
        return phoneEntity?.let {
            PhoneTest(
                city = it.city,
                comment = it.comment,
                country = it.country,
                number = it.number
            )
        }
    }



    fun vacancyVacancyToEntity(vacancy: VacancyForTests): FavoriteVacancyEntity {
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

    private fun createSalaryEntity(salary: SalaryTest?): SalaryEntity? {
        return salary?.let {
            SalaryEntity(
                currency = it.currency,
                from = it.from,
                gross = it.gross,
                to = it.to
            )
        }
    }

    private fun createContactsEntity(contacts: ContactsTest?): ContactsEntity? {
        return contacts?.let {
            ContactsEntity(
                email = it.email,
                name = it.name,
                phones = it.phones?.map { createPhoneEntity(it) }
            )
        }
    }

    private fun createPhoneEntity(phone: PhoneTest?): PhoneEntity? {
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
