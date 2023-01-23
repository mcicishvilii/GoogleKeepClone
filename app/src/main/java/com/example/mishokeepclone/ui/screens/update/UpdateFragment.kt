package com.example.mishokeepclone.ui.screens.update

import android.util.Log
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
import java.util.*

@AndroidEntryPoint
class UpdateFragment : BaseFragment<FragmentUpdateBinding>(FragmentUpdateBinding::inflate) {

    private val vm: UpdateViewModel by viewModels()
    val args:UpdateFragmentArgs by navArgs()

    override fun viewCreated() {

        binding.etUpdatetitle.setText(args.info.title)
        binding.etUpdateDescription.setText(args.info.taskDescription)
        binding.etCoworkerSpinner.setText(args.info.priority)
        setupSpinner()
    }

    override fun listeners() {
        binding.updateNutton.setOnClickListener {
            updateTask()
        }
    }

    private fun updateTask(){

        val priority = binding.etCoworkerSpinner.text.toString()

        val updatedTask = TaskEntity(
            args.info.taskid,
            binding.etUpdatetitle.text.toString(),
            binding.etUpdateDescription.text.toString(),
            priority)
        vm.updateTask(updatedTask)
        findNavController().navigate(UpdateFragmentDirections.actionUpdateFragmentToDashboardFragment())
    }

    private fun setupSpinner() {
        val priority = resources.getStringArray(R.array.priority)
        val adapter1 = ArrayAdapter(requireContext(), R.layout.custom_spinner_layout, priority)
        binding.etCoworkerSpinner.setAdapter(adapter1)
    }
}