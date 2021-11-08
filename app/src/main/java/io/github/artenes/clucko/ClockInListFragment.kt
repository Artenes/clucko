package io.github.artenes.clucko

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.TooltipCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.github.artenes.clucko.databinding.FragmentClockInListBinding

@AndroidEntryPoint
class ClockInListFragment : Fragment(), View.OnClickListener {

    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentClockInListBinding.inflate(inflater, container, false)

        val adapter = ClockInsAdapter(model)
        binding.rvClockIns.adapter = adapter
        binding.rvClockIns.layoutManager = LinearLayoutManager(requireContext())

        binding.imageDayHours.setOnClickListener(this)
        binding.txtDate.setOnClickListener(this)
        binding.iconEdit.setOnClickListener(this)

        TooltipCompat.setTooltipText(binding.txtDate, getString(R.string.edit_current_date))
        TooltipCompat.setTooltipText(binding.iconEdit, getString(R.string.edit_current_date))
        TooltipCompat.setTooltipText(binding.imageDayHours, getString(R.string.edit_daily_workload))

        model.clockIns.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.viewEmpty.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        }

        model.balance.observe(viewLifecycleOwner) {
            binding.txtBalance.text = it
        }

        model.left.observe(viewLifecycleOwner) {
            binding.txtLeft.text = it
        }

        model.date.observe(viewLifecycleOwner) {
            binding.txtDate.text = it
        }

        model.editClockIn.observe(viewLifecycleOwner) { event ->
            event.get()?.let {
                val intent = Intent(requireContext(), EditClockInActivity::class.java)
                intent.putExtra("timestamp", it)
                startActivity(intent)
            }
        }

        return binding.root
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.imageDayHours -> startActivity(Intent(requireContext(), EditHoursPerDayActivity::class.java))
            else -> startActivity(Intent(requireContext(), EditCurrentDayActivity::class.java))
        }
    }
}