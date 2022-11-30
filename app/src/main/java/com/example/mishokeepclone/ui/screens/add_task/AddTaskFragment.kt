package com.example.mishokeepclone.ui.screens.add_task

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mishokeepclone.R
import com.example.mishokeepclone.common.BaseFragment
import com.example.mishokeepclone.data.TaskEntity
import com.example.mishokeepclone.databinding.FragmentAddTaskBinding
import dagger.hilt.android.AndroidEntryPoint

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


