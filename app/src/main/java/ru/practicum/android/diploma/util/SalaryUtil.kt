package ru.practicum.android.diploma.util

import android.content.Context
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.domain.models.Salary
import java.text.NumberFormat
import java.util.Locale

class SalaryUtil {
    companion object {

        private fun getCurrencySymbol(context: Context, currencyCode: String): String {
            val resourceId = when (currencyCode) {
                "RUR", "RUB" -> R.string.currency_symbol_rub
                "BYR" -> R.string.currency_symbol_byr
                "USD" -> R.string.currency_symbol_usd
                "EUR" -> R.string.currency_symbol_eur
                "KZT" -> R.string.currency_symbol_kzt
                "UAH" -> R.string.currency_symbol_uah
                "AZN" -> R.string.currency_symbol_azn
                "UZS" -> R.string.currency_symbol_uzs
                "GEL" -> R.string.currency_symbol_gel
                "KGT" -> R.string.currency_symbol_kgt
                else -> {
                    return currencyCode
                }
            }
            return context.getString(resourceId)
        }

        fun formatNumberWithSpaces(number: Int): String {
            val formatter = NumberFormat.getNumberInstance(Locale.FRENCH)
            return formatter.format(number)
        }

        fun formSalary(salary: Salary?, context: Context): String {
            salary ?: return context.getString(R.string.salary_not_indicated)

            return when {
                salary.from != null && salary.to != null && salary.currency != null ->
                    context.getString(
                        R.string.salary_from_to,
                        formatNumberWithSpaces(salary.from),
                        formatNumberWithSpaces(salary.to),
                        getCurrencySymbol(context, salary.currency)
                    )

                salary.from != null && salary.currency != null ->
                    context.getString(
                        R.string.salary_from,
                        formatNumberWithSpaces(salary.from),
                        getCurrencySymbol(context, salary.currency)
                    )

                salary.to != null && salary.currency != null ->
                    context.getString(
                        R.string.salary_to,
                        formatNumberWithSpaces(salary.to),
                        getCurrencySymbol(context, salary.currency)
                    )

                else -> context.getString(R.string.salary_not_indicated)
            }
        }

    }
}