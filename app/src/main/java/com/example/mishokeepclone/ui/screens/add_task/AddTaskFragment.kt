package com.example.mishokeepclone.ui.screens.add_task

import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mishokeepclone.R
import com.example.mishokeepclone.common.*
import com.example.mishokeepclone.common.notif.NotiReceiver
import com.example.mishokeepclone.common.notif.messageExtra
import com.example.mishokeepclone.common.notif.notificationID
import com.example.mishokeepclone.common.notif.titleExtra
import com.example.mishokeepclone.data.local.TaskEntity
import com.example.mishokeepclone.databinding.FragmentAddTaskBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class AddTaskFragment : BaseFragment<FragmentAddTaskBinding>(FragmentAddTaskBinding::inflate),AdapterView.OnItemSelectedListener {
    private var priority: String = ""
    private val vm: AddTaskViewModel by viewModels()

    override fun viewCreated() {
        setupSpinner()
    }

    @RequiresApi(Build.VERSION_CODES.M)

    override fun listeners() {
        addItem()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun scheduleNotification()
    {
        val intent = Intent(context, NotiReceiver::class.java)

        val title = binding.etTitle.text.toString()
        val message = binding.etDescription.text.toString()
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            getTime(),
            pendingIntent
        )
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun addItem() {
        binding.addNutton.setOnClickListener {
            val task = TaskEntity(
                0,
                binding.etTitle.text.toString(),
                binding.etDescription.text.toString(),
                priority,
                getTimeForList()
            )

            vm.insertTask(task)
            Log.d("mcicishv",getTimeForList().toString())
            scheduleNotification()
            findNavController().navigate(R.id.action_addTaskFragment_to_dashboardFragment)

        }
    }

    private fun setupSpinner() {

        val priority = resources.getStringArray(R.array.priority)
        val adapter1 = ArrayAdapter(requireContext(),R.layout.custom_spinner_layout,priority)
        binding.newspinner.setAdapter(adapter1)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val text: String = parent?.getItemAtPosition(position).toString()
        priority = text

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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getTimeForList(): String
    {
        val minute = binding.timePicker.minute
        val hour = binding.timePicker.hour
        val day = binding.datePicker.dayOfMonth
        val month = binding.datePicker.month
        val year = binding.datePicker.year

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.time.toString()
    }

}



