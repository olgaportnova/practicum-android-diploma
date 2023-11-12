package ru.practicum.android.diploma.favorite.recycle_view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemViewholderVacancyBinding
import ru.practicum.android.diploma.search.domain.models.Vacancy

class VacancyAdapter(
    private val vacancyList: ArrayList<Vacancy>,
    private var onClickListener: OnClickListener
) : RecyclerView.Adapter<VacancyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemViewholderVacancyBinding.inflate(inflater, parent, false)
        return VacancyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return vacancyList.size
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancyList[position], onClickListener)
    }

    interface OnClickListener {
        fun onItemClick(vacancy: Vacancy)
    }

    @SuppressLint("SuspiciousIndentation")
    fun updateList(
        newVacancyList: List<Vacancy>,
        isPagination: Boolean = false,
        newSearch: Boolean = false
    ) {
        if (isPagination) {
           /* if (newSearch){
                val diffResult =
                    DiffUtil.calculateDiff(VacancyDiffCallback(vacancyList, newVacancyList))
                vacancyList.clear()
                vacancyList.addAll(newVacancyList)
                diffResult.dispatchUpdatesTo(this)
            }*/
            val newListForCompare = vacancyList.map { it } as ArrayList<Vacancy>
            newListForCompare.addAll(newVacancyList)
            vacancyList.addAll(newVacancyList)
            val diffResult =
                DiffUtil.calculateDiff(
                    VacancyDiffCallback(
                        vacancyList,
                        newListForCompare as List<Vacancy>
                    )
                )
            diffResult.dispatchUpdatesTo(this)
        } else {
            val diffResult =
                DiffUtil.calculateDiff(VacancyDiffCallback(vacancyList, newVacancyList))

            vacancyList.clear()
            vacancyList.addAll(newVacancyList)
            diffResult.dispatchUpdatesTo(this)
        }
    }

}
