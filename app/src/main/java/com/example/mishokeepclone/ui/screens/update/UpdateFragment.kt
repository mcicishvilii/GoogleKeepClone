package com.example.mishokeepclone.ui.screens.update

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mishokeepclone.R
import com.example.mishokeepclone.common.BaseFragment
import com.example.mishokeepclone.data.local.TaskEntity
import com.example.mishokeepclone.databinding.FragmentUpdateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateFragment : BaseFragment<FragmentUpdateBinding>(FragmentUpdateBinding::inflate),AdapterView.OnItemSelectedListener {

    private var priority: String = ""
    private val vm: UpdateViewModel by viewModels()
    val args:UpdateFragmentArgs by navArgs()

    override fun viewCreated() {
        setupSpinner()
        binding.etUpdatetitle.setText(args.info.title)
        binding.etUpdateDescription.setText(args.info.taskDescription)
    }

    override fun listeners() {
        binding.updateNutton.setOnClickListener {
            updateTask()
        }
    }

    private fun updateTask(){
        val updatedTask = TaskEntity(
            args.info.taskid,
            binding.etUpdatetitle.text.toString(),
            binding.etUpdateDescription.text.toString(),
            priority)
        vm.updateTask(updatedTask)
        findNavController().navigate(UpdateFragmentDirections.actionUpdateFragmentToDashboardFragment())
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