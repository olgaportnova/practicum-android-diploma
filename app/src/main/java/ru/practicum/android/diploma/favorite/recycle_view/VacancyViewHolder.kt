package ru.practicum.android.diploma.favorite.recycle_view

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemViewholderVacancyBinding
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.SalaryUtil

class VacancyViewHolder(
    private val binding: ItemViewholderVacancyBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val radius = binding.root.resources.getDimensionPixelSize(R.dimen.corners_radius_art_work_vacancy)

    fun bind(vacancy: Vacancy, onClickListener: VacancyAdapter.OnClickListener) {

        with(binding) {
            headerVacancy.text = buildString {
                append(vacancy.vacancyName)
                vacancy.city?.takeIf { it.isNotEmpty() }?.let {
                    append(", $it")
                }
            }
            companyVacancy.text = vacancy.companyName
            salaryVacancy.text = SalaryUtil.formSalary(vacancy.salary, itemView.context)
            Glide
                .with(itemView)
                .load(vacancy.logoUrl)
                .placeholder(R.drawable.placeholder_employer_logo)
                .centerCrop()
                .transform(RoundedCorners(radius))
                .into(binding.artWork)
        }
        itemView.setOnClickListener {
            onClickListener.onItemClick(vacancy)
        }
    }
}


