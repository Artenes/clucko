package io.github.artenes.clucko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import io.github.artenes.clucko.databinding.ActivityEditClockInBinding

@AndroidEntryPoint
class EditClockInActivity : AppCompatActivity() {

    private val model: EditClockInViewModel by viewModels()

    private lateinit var binding: ActivityEditClockInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_clock_in)

        val timestamp = intent.getLongExtra("timestamp", 0)
        model.init(timestamp)

        model.timeText.observe(this) {
            binding.editTime.setText(it)
        }
    }
}