package io.github.artenes.clucko.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import io.github.artenes.clucko.viewmodels.EditClockInViewModel
import io.github.artenes.clucko.R
import io.github.artenes.clucko.databinding.ActivityEditClockInBinding

@AndroidEntryPoint
class EditClockInActivity : AppCompatActivity(), View.OnClickListener, View.OnLongClickListener {

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

        binding.buttonDelete.setOnClickListener(this)
        binding.buttonDelete.setOnLongClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.buttonSave -> model.save()
            R.id.buttonDelete -> Toast.makeText(this, "Long press to delete", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onLongClick(v: View?): Boolean {
        model.delete()
        return true
    }
}