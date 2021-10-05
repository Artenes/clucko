package io.github.artenes.clucko

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.artenes.clucko.databinding.ClockinItemBinding

class ClockInsAdapter() : ListAdapter<String, ClockInsAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private val binding: ClockinItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clockInText: String, index: Int) {
            binding.clockIn = clockInText
            binding.isIn = index % 2 == 0
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ClockinItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.clockin_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    companion object {

        val DiffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem.contentEquals(newItem)
        }

    }

}