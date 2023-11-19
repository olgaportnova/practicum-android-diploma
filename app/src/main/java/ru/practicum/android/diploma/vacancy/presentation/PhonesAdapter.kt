package ru.practicum.android.diploma.vacancy.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemViewholderPhoneBinding
import ru.practicum.android.diploma.search.domain.models.Phone

class PhonesAdapter(
    private val phones: List<Phone?>,
    private val clickListener: (Phone) -> Unit
) : RecyclerView.Adapter<PhoneViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PhoneViewHolder(ItemViewholderPhoneBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
        phones[position]?.let { holder.bind(it) }
        holder.itemView.setOnClickListener {
            phones[position]?.let { phone -> clickListener.invoke(phone) }
        }
    }

    override fun getItemCount(): Int {
        return phones.size
    }
}