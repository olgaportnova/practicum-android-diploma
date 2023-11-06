package ru.practicum.android.diploma.util.mappers

import ru.practicum.android.diploma.search.data.dto.models.AnswerVacancyListDto
import ru.practicum.android.diploma.search.domain.models.AnswerVacancyList

object AnswerSearchDtoMapper {
    fun answSearchDtoInSearch(answerModel: AnswerVacancyListDto): AnswerVacancyList {
        val vacancyDtoMapper = VacancyDtoMapper()

        return with(answerModel) {
            AnswerVacancyList(
                found = found,
                maxPages = maxPages,
                currentPages = currentPages,
                listVacancy = listVacancy.map { vacancyDto ->
                    vacancyDtoMapper.vacancyDtoToVacancy(
                        vacancyDto
                    )
                }
            )
        }
    }
}