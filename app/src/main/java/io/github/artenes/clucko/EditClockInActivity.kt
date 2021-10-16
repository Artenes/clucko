package io.github.artenes.clucko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import io.github.artenes.clucko.databinding.ActivityEditClockInBinding

@AndroidEntryPoint
class EditClockInActivity : AppCompatActivity(), View.OnClickListener {

    private val model: EditClockInViewModel by viewModels()

    private lateinit var binding: ActivityEditClockInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_clock_in)

        binding.pickerHour.minValue = 0
        binding.pickerHour.maxValue = 24
        binding.pickerMinute.minValue = 0
        binding.pickerMinute.maxValue = 59

        val timestamp = intent.getLongExtra("timestamp", 0)
        model.init(timestamp)

        model.hourTimeLiveData.observe(this) {
            binding.pickerHour.value = it.hour
            binding.pickerMinute.value = it.minute
        }

        model.closeEvent.observe(this) { event ->
            event.get()?.let {
                finish()
            }
        }

        binding.pickerHour.setOnValueChangedListener { picker, oldVal, newVal -> model.setHour(newVal) }
        binding.pickerMinute.setOnValueChangedListener { picker, oldVal, newVal -> model.setMinute(newVal) }

        binding.buttonSave.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        model.save()
    }
}