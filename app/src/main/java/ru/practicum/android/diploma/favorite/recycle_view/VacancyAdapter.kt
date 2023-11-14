package ru.practicum.android.diploma.favorite.recycle_view

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

    fun updateList(newVacancyList: List<Vacancy>) {
        val diffResult = DiffUtil.calculateDiff(VacancyDiffCallback(vacancyList, newVacancyList))
        vacancyList.clear()
        vacancyList.addAll(newVacancyList)
        diffResult.dispatchUpdatesTo(this)
    }

}
