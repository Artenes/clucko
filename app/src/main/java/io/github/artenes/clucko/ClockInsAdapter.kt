package io.github.artenes.clucko

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.artenes.clucko.databinding.ClockinItemBinding

class ClockInsAdapter(private val dataSet: MutableList<String>) :
    RecyclerView.Adapter<ClockInsAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ClockinItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(clockInText: String, index: Int) {
            binding.clockIn = clockInText
            binding.isIn = index % 2 == 0
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ClockinItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.clockin_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position], position)
    }

    override fun getItemCount(): Int = dataSet.size

}