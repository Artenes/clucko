package io.github.artenes.clucko

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.github.artenes.clucko.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val model: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: ClockInsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        adapter = ClockInsAdapter()
        binding.rvClockIns.adapter = adapter
        binding.rvClockIns.layoutManager = LinearLayoutManager(this)
        binding.fabClockIn.setOnClickListener(this)

        model.clockIns.observe(this) {
            adapter.submitList(it)
        }
    }

    override fun onClick(v: View?) {
        //https://www.rockandnull.com/java-time-android/
        //https://stackoverflow.com/questions/32437550/whats-the-difference-between-instant-and-localdatetime
        model.putClockIn()
    }
}