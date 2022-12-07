package com.example.mishokeepclone.ui.screens.add_task

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mishokeepclone.R
import com.example.mishokeepclone.common.BaseFragment
import com.example.mishokeepclone.common.channelID
import com.example.mishokeepclone.data.local.TaskEntity
import com.example.mishokeepclone.databinding.FragmentAddTaskBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class AddTaskFragment : BaseFragment<FragmentAddTaskBinding>(FragmentAddTaskBinding::inflate),
    AdapterView.OnItemSelectedListener {


    private var priority: String = ""
    private val vm: AddTaskViewModel by viewModels()

    override fun viewCreated() {
        setupSpinner()

    }

    override fun listeners() {
        addItem()
    }

    private fun addItem() {
        binding.addNutton.setOnClickListener {
            val task = TaskEntity(
                0,
                binding.etTitle.text.toString(),
                binding.etDescription.text.toString(),
                priority
            )

            vm.insertTask(task)
            findNavController().navigate(R.id.action_addTaskFragment_to_dashboardFragment)

        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getTime(): Long
    {
        val minute = binding.timePicker.minute
        val hour = binding.timePicker.hour
        val day = binding.datePicker.dayOfMonth
        val month = binding.datePicker.month
        val year = binding.datePicker.year
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }


    private fun setupSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.priority,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = this
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val text: String = parent?.getItemAtPosition(position).toString()
        priority = text

    }
}



