package io.github.artenes.clucko

import android.content.Intent
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

        val adapter = ClockInsAdapter(item)
        binding.rvClockIns.adapter = adapter
        binding.rvClockIns.layoutManager = LinearLayoutManager(requireContext())

        item.clockIns.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.viewEmpty.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
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

        item.editClockIn.observe(viewLifecycleOwner) { event ->
            event.get()?.let {
                val intent = Intent(requireContext(), EditClockInActivity::class.java)
                intent.putExtra("timestamp", it)
                startActivity(intent)
            }
        }

        return binding.root
    }

}