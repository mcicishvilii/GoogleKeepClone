package com.example.mishokeepclone.ui.screens.add_task

import android.annotation.SuppressLint
import android.app.*
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
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class AddTaskFragment : BaseFragment<FragmentAddTaskBinding>(FragmentAddTaskBinding::inflate) {

    private var time:Long = 0
    private val vm: AddTaskViewModel by viewModels()

    val c = Calendar.getInstance();
    val mYear = c.get(Calendar.YEAR);
    val mMonth = c.get(Calendar.MONTH);
    val mDay = c.get(Calendar.DAY_OF_MONTH);

    override fun viewCreated() {
        setupSpinner()
    }

    override fun listeners() {
        showDatePickerDialog()
        addItem()
    }


    @SuppressLint("SuspiciousIndentation")
    private fun dialog(task: TaskEntity){

        val time = "${binding.tvTime.text}000"
        val formatedTime = TimeFormaterIMPL().formatMillisToDateAndSplit(time.toLong())
            AlertDialog.Builder(requireContext())
            .setTitle("Connfirm Date")
            .setMessage("please confirm the date $formatedTime")
            .setPositiveButton("Confirm"){dialog,id ->
                executeAdd(task)
                scheduleNotification(time.toLong())

            }
            .setNegativeButton("Cancel"){dialog,id ->

            }
            .show()
    }


    private fun scheduleNotification(exactTime:Long)
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
//        val timeInAM = time
        Log.d("mishocicka", "${binding.btnDate.text} dro alarmmenejershi")
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            exactTime,
            pendingIntent
        )

        Log.d("mishocicka","${alarmManager.nextAlarmClock}")

    }

    private fun executeAdd(task:TaskEntity){
        vm.insertTask(task)
        findNavController().navigate(R.id.action_addTaskFragment_to_dashboardFragment)
    }

    private fun addItem() {
        binding.addNutton.setOnClickListener {

            val coworker = binding.newspinner.text.toString()

            val task = TaskEntity(
                0,
                binding.etTitle.text.toString(),
                binding.etDescription.text.toString(),
                coworker,
            )
            dialog(task)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showDatePickerDialog() {
        var unix: Long = 0
        binding.btnDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    var month = ""
                    when (monthOfYear.toString().length) {
                        1 -> {
                            month = String.format("%02d", monthOfYear + 1)
                        }
                        2 -> {

                        }
                    }
                    val date = buildString {
                        append(dayOfMonth)
                        append("-")
                        append(month)
                        append("-")
                        append(year)
                    }

                    val l = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    unix = l.atStartOfDay(ZoneId.systemDefault()).toInstant().epochSecond

                    binding.btnDate.text = date
                    binding.tvTime.text = unix.toString()
                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()

        }
    }

    private fun setupSpinner() {
        val priority = resources.getStringArray(R.array.priority)
        val adapter1 = ArrayAdapter(requireContext(), R.layout.custom_spinner_layout, priority)
        binding.newspinner.setAdapter(adapter1)
    }
}



