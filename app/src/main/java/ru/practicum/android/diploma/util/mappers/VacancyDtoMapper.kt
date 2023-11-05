package ru.practicum.android.diploma.util.mappers

import ru.practicum.android.diploma.search.data.dto.models.ContactsDto
import ru.practicum.android.diploma.search.data.dto.models.KeySkillDto
import ru.practicum.android.diploma.search.data.dto.models.PhoneDto
import ru.practicum.android.diploma.search.data.dto.models.SalaryDto
import ru.practicum.android.diploma.search.data.dto.models.VacancyDto
import ru.practicum.android.diploma.search.domain.models.Contacts
import ru.practicum.android.diploma.search.domain.models.Phone
import ru.practicum.android.diploma.search.domain.models.Salary
import ru.practicum.android.diploma.search.domain.models.Vacancy

class VacancyDtoMapper {

    fun vacancyDtoToVacancy(vacancyDto: VacancyDto): Vacancy {
        return with(vacancyDto) {
            Vacancy(
                id = id,
                vacancyName = vacancyName,
                companyName = employer.name,
                logoUrl = logo?.small,
                city = area.name,
                employment = employment?.name,
                experience = experience?.name,
                salary = createSalary(salary),
                description = description,
                keySkills = extractKeySkills(keySkills),
                contacts = createContacts(contacts),
                comment = comment
            )
        }
    }

    private fun createSalary(salaryDto: SalaryDto?): Salary? {
        return salaryDto?.let {
            Salary(
                currency = it.currency,
                from = it.from,
                gross = it.gross,
                to = it.to
            )
        }
    }

    private fun extractKeySkills(keySkills: List<KeySkillDto>?): List<String?> {
        return keySkills?.map { it.name } ?: emptyList()
    }

    private fun createContacts(contactsDto: ContactsDto?): Contacts? {
        return contactsDto?.let {
            Contacts(
                email = it.email,
                name = it.name,
                phones = createPhone(it.phones)
            )
        }
    }

    private fun createPhone(phoneDto: PhoneDto?): Phone? {
        return phoneDto?.let {
            Phone(
                city = it.city,
                comment = it.comment,
                country = it.country,
                number = it.number
            )
        }
    }

}
