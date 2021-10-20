package io.github.artenes.clucko

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import io.github.artenes.clucko.databinding.ActivityEditHoursPerDayBinding

@AndroidEntryPoint
class EditHoursPerDayActivity : AppCompatActivity(), View.OnClickListener {

    val model: EditHoursPerDayViewModel by viewModels()

    lateinit var binding: ActivityEditHoursPerDayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_edit_hours_per_day
        )

        binding.pickerHour.minValue = 0
        binding.pickerHour.maxValue = 24
        binding.pickerMinute.minValue = 0
        binding.pickerMinute.maxValue = 59

        model.hoursPerDay.observe(this) {
            binding.pickerHour.value = it.hour
            binding.pickerMinute.value = it.minute
        }

        model.closeEvent.observe(this) {
            it.get()?.let {
                finish()
            }
        }

        binding.pickerHour.setOnValueChangedListener { picker, oldVal, newVal ->
            model.setHour(
                newVal
            )
        }
        binding.pickerMinute.setOnValueChangedListener { picker, oldVal, newVal ->
            model.setMinute(
                newVal
            )
        }

        binding.buttonSave.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        model.save()
    }
}