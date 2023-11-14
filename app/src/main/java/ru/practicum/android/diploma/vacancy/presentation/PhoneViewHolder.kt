package ru.practicum.android.diploma.vacancy.presentation

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemViewholderPhoneBinding
import ru.practicum.android.diploma.search.domain.models.Phone
import ru.practicum.android.diploma.util.PhonesUtil

class PhoneViewHolder(
    private val binding: ItemViewholderPhoneBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(phone: Phone) {
        val formatNumber = PhonesUtil.numberFormatting(phone)

        binding.apply {
            phoneValue.text = formatNumber
            phoneComment.text = phone.comment
        }
    }
}