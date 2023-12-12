package com.zenith.ecoscan.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zenith.ecoscan.databinding.ItemHistoryBinding
import com.zenith.ecoscan.domain.entities.ItemData

class HistoryAdapter (private val onClickItem: (data: ItemData) -> Unit) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private var listHistory = emptyList<ItemData>()

    class ViewHolder(var binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listHistory.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listHistory[position]
        with(holder.binding) {
            tvName.text = item.name
            tvDate.text = item.time

            Glide.with(holder.itemView.context)
                .load(item.image)
                .centerCrop()
                .into(ivPreview)

            root.setOnClickListener {
                val data = ItemData(
                    item.lokasi,
                    item.averageEnergy,
                    item.dampakDisposal,
                    item.sumber,
                    item.dampakProduksi,
                    item.name,
                    item.dampakKonsumsi,
                    item.image,
                    item.recommendations,
                    item.dampakDisposalPanjang,
                    item.dampakProduksiPanjang,
                    item.dampakKonsumsiPanjang,
                    item.result,
                )
                onClickItem(data)
            }
        }
    }

    fun setData(data: List<ItemData>) {
        val diffUtil = HistoryCallback(listHistory, data)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        listHistory = data
        diffResult.dispatchUpdatesTo(this)
    }

}

class HistoryCallback(private val oldList: List<ItemData>, private val newList: List<ItemData>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].name != newList[newItemPosition].name -> {
                false
            }
            else -> true
        }
    }

}