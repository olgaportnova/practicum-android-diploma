package ru.practicum.android.diploma.favorite.recycle_view

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemViewholderVacancyBinding
import ru.practicum.android.diploma.search.domain.models.Salary
import ru.practicum.android.diploma.search.domain.models.Vacancy
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

class VacancyViewHolder(
    private val binding: ItemViewholderVacancyBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(vacancy: Vacancy) {

        with(binding) {
            headerVacancy.text = "${vacancy.vacancyName}${
                vacancy.city?.takeIf { it.isNotEmpty() }?.let { ", $it" } ?: ""
            }"
            companyVacancy.text = vacancy.companyName
            salaryVacancy.text = formSalary(vacancy.salary, itemView.context)
            Glide
                .with(itemView)
                .load(vacancy.logoUrl)
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .transform(RoundedCorners(R.dimen.corners_radius_art_work_vacancy))
                .into(binding.artWork)
        }

    }

    private fun formSalary(salary: Salary?, context: Context): String {
        salary ?: return context.getString(R.string.salary_not_indicated)

        return when {
            salary.from != null && salary.to != null && salary.currency != null ->
                context.getString(
                    R.string.salary_from_to,
                    formatNumberWithSpaces(salary.from),
                    formatNumberWithSpaces(salary.to),
                    getCurrencySymbol(salary.currency)
                )

            salary.from != null && salary.currency != null ->
                context.getString(
                    R.string.salary_from,
                    formatNumberWithSpaces(salary.from),
                    getCurrencySymbol(salary.currency)
                )

            salary.to != null && salary.currency != null ->
                context.getString(
                    R.string.salary_to,
                    formatNumberWithSpaces(salary.to),
                    getCurrencySymbol(salary.currency)
                )

            else -> context.getString(R.string.salary_not_indicated)
        }
    }

    private fun formatNumberWithSpaces(number: Int): String {
        val formatter = NumberFormat.getNumberInstance(Locale.FRENCH)
        return formatter.format(number)
    }

    private fun getCurrencySymbol(currencyCode: String): String {
        return try {
            Currency.getInstance(currencyCode).symbol
        } catch (e: IllegalArgumentException) {
            currencyCode
        }
    }


}