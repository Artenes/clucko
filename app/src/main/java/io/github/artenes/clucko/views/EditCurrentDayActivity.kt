package io.github.artenes.clucko.views

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import io.github.artenes.clucko.viewmodels.EditCurrentDayViewModel
import io.github.artenes.clucko.R
import io.github.artenes.clucko.databinding.ActivityEditCurrentDayBinding

@AndroidEntryPoint
class EditCurrentDayActivity : AppCompatActivity(), View.OnClickListener {

    val model: EditCurrentDayViewModel by viewModels()

    lateinit var binding: ActivityEditCurrentDayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_edit_current_day
        )

        binding.pickerDay.minValue = 1
        binding.pickerDay.maxValue = 31
        binding.pickerMonth.minValue = 1
        binding.pickerMonth.maxValue = 12
        binding.pickerYear.minValue = 2010
        binding.pickerYear.maxValue = 2060

        model.displayDate.observe(this) {
            binding.pickerDay.value = it.day
            binding.pickerMonth.value = it.month
            binding.pickerYear.value = it.year
        }

        model.dayMaxValue.observe(this) {
            binding.pickerDay.maxValue = it
        }

        model.closeEvent.observe(this) {
            it.get()?.let {
                finish()
            }
        }

        binding.pickerDay.setOnValueChangedListener { picker, oldVal, newVal ->
            model.setDay(newVal)
        }

        binding.pickerMonth.setOnValueChangedListener { picker, oldVal, newVal ->
            model.setMonth(newVal)
        }

        binding.pickerYear.setOnValueChangedListener { picker, oldVal, newVal ->
            model.setYear(newVal)
        }

        binding.buttonSave.setOnClickListener(this)
        binding.buttonToday.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        if (v.id == R.id.buttonSave) {
            model.save()
        } else {
            model.setAsToday()
        }

    }
}