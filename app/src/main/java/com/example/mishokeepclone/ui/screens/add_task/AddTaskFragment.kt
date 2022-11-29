package com.example.mishokeepclone.ui.screens.add_task

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mishokeepclone.R
import com.example.mishokeepclone.common.BaseFragment
import com.example.mishokeepclone.data.TaskEntity
import com.example.mishokeepclone.databinding.FragmentAddTaskBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskFragment : BaseFragment<FragmentAddTaskBinding>(FragmentAddTaskBinding::inflate) {

    private val vm: AddTaskViewModel by viewModels()
    override fun viewCreated() {
    }

    override fun listeners() {

        binding.addNutton.setOnClickListener {
            val task = TaskEntity(
                0,
                binding.etTitle.text.toString(),
                binding.etDescription.text.toString()
            )
            vm.insertTask(task)
            findNavController().navigate(R.id.action_addTaskFragment_to_dashboardFragment)
        }
    }
}