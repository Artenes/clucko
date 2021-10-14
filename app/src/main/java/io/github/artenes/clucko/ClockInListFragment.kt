package io.github.artenes.clucko

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.github.artenes.clucko.databinding.FragmentClockInListBinding

@AndroidEntryPoint
class ClockInListFragment : Fragment() {

    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentClockInListBinding.inflate(inflater, container, false)

        val index = requireArguments().getInt("index")
        val item = model.getItem(index)

        val adapter = ClockInsAdapter()
        binding.rvClockIns.adapter = adapter
        binding.rvClockIns.layoutManager = LinearLayoutManager(requireContext())

        item.clockIns.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        item.balance.observe(viewLifecycleOwner) {
            binding.txtBalance.text = it
        }

        item.left.observe(viewLifecycleOwner) {
            binding.txtLeft.text = it
        }

        item.date.observe(viewLifecycleOwner) {
            binding.txtDate.text = it
        }

        return binding.root
    }

}