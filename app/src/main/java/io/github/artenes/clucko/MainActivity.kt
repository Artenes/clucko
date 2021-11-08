package io.github.artenes.clucko

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint
import io.github.artenes.clucko.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : FragmentActivity(), View.OnClickListener {

    private val model: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.model = model
        binding.fabClockIn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        //https://www.rockandnull.com/java-time-android/
        //https://stackoverflow.com/questions/32437550/whats-the-difference-between-instant-and-localdatetime
        model.putClockIn()
    }
}