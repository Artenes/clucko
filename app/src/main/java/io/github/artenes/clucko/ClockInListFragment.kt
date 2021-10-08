package io.github.artenes.clucko

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.github.artenes.clucko.databinding.FragmentClockInListBinding

@AndroidEntryPoint
class ClockInListFragment : Fragment() {

    private val model: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentClockInListBinding.inflate(inflater, container, false)

        model.init(Time(requireArguments().getLong("time")))

        binding.model = model

        val adapter = ClockInsAdapter()
        binding.rvClockIns.adapter = adapter
        binding.rvClockIns.layoutManager = LinearLayoutManager(requireContext())

        model.clockIns.observe(viewLifecycleOwner) {
            adapter.submitList(it)
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

        return binding.root
    }

}