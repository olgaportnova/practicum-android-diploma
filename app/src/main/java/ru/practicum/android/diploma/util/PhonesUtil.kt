package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.search.domain.models.Phone

class PhonesUtil {
    companion object {
        fun numberFormatting(phone: Phone): String {
            return String.format(
                "+%s (%s) %s-%s-%s",
                phone.country,
                phone.city,
                phone.number?.dropLast(4),
                phone.number?.drop(3)?.dropLast(2),
                phone.number?.drop(5)
            )
        }
    }
}