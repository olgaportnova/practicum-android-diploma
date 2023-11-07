package ru.practicum.android.diploma.filter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import ru.practicum.android.diploma.databinding.ItemViewHolderAreaBinding
import ru.practicum.android.diploma.databinding.ItemViewHolderProfessionBinding
import ru.practicum.android.diploma.filter.domain.models.AbstractData

class AreaAdapter(
    private val areaList: MutableList<AbstractData>,
    private var onItemClickListener: Clickable
) : Adapter<AreaAdapter.InfoVH>() {
    private var adapterType:Int= AREA
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoVH {
        when(viewType){
            AREA -> {
                val binding = ItemViewHolderAreaBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return AreaViewHolder(binding)
            }
            CATEGORIES -> {
                val binding = ItemViewHolderProfessionBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return CategoriesViewHolder(binding)
            }
            else -> return InfoVH(ItemViewHolderAreaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
        }
    }

    override fun getItemCount() = areaList.size

    override fun onBindViewHolder(holder: InfoVH, position: Int) {
        holder.bindInfo(areaList[position], onItemClickListener)
    }

    override fun getItemViewType(position: Int): Int {
        return adapterType
    }

    fun changeData(newDataList: List<AbstractData>) {
        // TODO: insert diffUtil if needed
        val diffResult = DiffUtil.calculateDiff(AreaDiffCallback(areaList, newDataList))
        areaList.clear()
        areaList.addAll(newDataList)
        diffResult.dispatchUpdatesTo(this)

        //areaList.clear()
        //areaList.addAll(newDataList)
        //this.notifyDataSetChanged()
    }

    fun setAdapterType(typeAdapter:Int){
        adapterType = when(typeAdapter){
            0-> AREA
            1-> CATEGORIES
            else -> AREA
        }
    }

    fun setNewItemClickListener(newClickListener: Clickable){
        this.onItemClickListener = newClickListener
    }

    open class InfoVH(open val binding:ViewBinding):ViewHolder(binding.root){
        open fun bindInfo(area: AbstractData, onItemClickListener: Clickable) {}
    }

    class AreaViewHolder(override val binding: ItemViewHolderAreaBinding) : InfoVH(binding) {
        override fun bindInfo(area: AbstractData, onItemClickListener: Clickable) {
            binding.txtAreaName.text = area.name
            binding.txtAreaName.setOnClickListener {
                onItemClickListener.onClick(area)
            }
        }
    }

    class CategoriesViewHolder(override val binding: ItemViewHolderProfessionBinding) : InfoVH(binding) {
        override fun bindInfo(area: AbstractData, onItemClickListener: Clickable) {
            binding.txtAreaName.text = area.name
            binding.btnRadio.isChecked = area.isSelected
            binding.txtAreaName.setOnClickListener {
                onItemClickListener.onClick(area)
            }
        }
    }

    fun interface Clickable {
        fun onClick(clickedAreaModel: AbstractData)
    }

    class AreaDiffCallback(
        private val oldList: List<AbstractData>,
        private val newList: List<AbstractData>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    companion object{
        const val AREA = 0
        const val CATEGORIES = 1

    }
}
