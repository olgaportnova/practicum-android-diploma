package ru.practicum.android.diploma.filter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.practicum.android.diploma.databinding.ItemViewHolderAreaBinding
import ru.practicum.android.diploma.filter.domain.models.Area

class AreaAdapter(
    private val areaList: MutableList<Area>,
    private val onItemClickListener: Clickable
) : Adapter<AreaAdapter.AreaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder {
        val bindingImpl =
            ItemViewHolderAreaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AreaViewHolder(bindingImpl)
    }

    override fun getItemCount() = areaList.size

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
        holder.bindInfo(areaList[position], onItemClickListener)
    }

    fun changeData(newDataList: List<Area>) {
        // TODO: insert diffUtil if needed
        areaList.clear()
        areaList.addAll(newDataList)
        this.notifyDataSetChanged()
    }

    class AreaViewHolder(val binding: ItemViewHolderAreaBinding) : ViewHolder(binding.root) {
        fun bindInfo(area: Area, onItemClickListener: Clickable) {
            binding.txtAreaName.text = area.name
            binding.txtAreaName.setOnClickListener {
                onItemClickListener.onClick(area)
            }
        }
    }


    fun interface Clickable {
        fun onClick(clickedAreaModel: Area)
    }
}
