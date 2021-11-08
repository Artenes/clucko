package io.github.artenes.clucko

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.artenes.clucko.databinding.ClockinItemBinding

class ClockInsAdapter(private val model: MainViewModel) : ListAdapter<ClockInItem, ClockInsAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: ClockinItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(clockInItem: ClockInItem) {
            binding.clockIn = clockInItem.time
            binding.isIn = clockInItem.isIn
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            model.editClockIn(adapterPosition)
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
        holder.bind(getItem(position))
    }

    companion object {

        val DiffCallback = object : DiffUtil.ItemCallback<ClockInItem>() {
            override fun areItemsTheSame(oldItem: ClockInItem, newItem: ClockInItem): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: ClockInItem, newItem: ClockInItem): Boolean =
                oldItem.hashCode() == newItem.hashCode()
        }

    }

}