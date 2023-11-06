package ru.practicum.android.diploma.util.mappers

import ru.practicum.android.diploma.search.data.dto.models.ContactsDto
import ru.practicum.android.diploma.search.data.dto.models.ContactsDtoTest
import ru.practicum.android.diploma.search.data.dto.models.KeySkillDto
import ru.practicum.android.diploma.search.data.dto.models.KeySkillDtoTest
import ru.practicum.android.diploma.search.data.dto.models.PhoneDto
import ru.practicum.android.diploma.search.data.dto.models.PhoneDtoTest
import ru.practicum.android.diploma.search.data.dto.models.SalaryDto
import ru.practicum.android.diploma.search.data.dto.models.SalaryDtoTest
import ru.practicum.android.diploma.search.data.dto.models.VacancyDto
import ru.practicum.android.diploma.search.data.dto.models.VacancyDtoTest
import ru.practicum.android.diploma.search.domain.models.Contacts
import ru.practicum.android.diploma.search.domain.models.ContactsTest
import ru.practicum.android.diploma.search.domain.models.Phone
import ru.practicum.android.diploma.search.domain.models.PhoneTest
import ru.practicum.android.diploma.search.domain.models.Salary
import ru.practicum.android.diploma.search.domain.models.SalaryTest
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.models.VacancyForTests

class VacancyDtoMapperTest {

    fun vacancyDtoToVacancy(vacancyDtoTest: VacancyDtoTest): VacancyForTests {
        return with(vacancyDtoTest) {
            VacancyForTests(
                id = id,
                vacancyName = vacancyName,
                companyName = employer.name,
                alternateUrl = alternateUrl,
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

    private fun createSalary(salaryDtoTest: SalaryDtoTest?): SalaryTest? {
        return salaryDtoTest?.let {
            SalaryTest(
                currency = it.currency,
                from = it.from,
                gross = it.gross,
                to = it.to
            )
        }
    }

    private fun extractKeySkills(keySkills: List<KeySkillDtoTest>?): List<String?> {
        return keySkills?.map { it.name } ?: emptyList()
    }

    private fun createContacts(contactsDtoTest: ContactsDtoTest?): ContactsTest? {
        return contactsDtoTest?.let {
            ContactsTest(
                email = it.email,
                name = it.name,
                phones = it.phones?.map { createPhone(it) }
            )
        }
    }

    private fun createPhone(phoneDtoTest: PhoneDtoTest?): PhoneTest? {
        return phoneDtoTest?.let {
            PhoneTest(
                city = it.city,
                comment = it.comment,
                country = it.country,
                number = it.number
            )
        }
    }

}
