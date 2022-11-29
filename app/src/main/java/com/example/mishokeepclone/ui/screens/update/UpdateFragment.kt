package com.example.mishokeepclone.ui.screens.update

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mishokeepclone.R
import com.example.mishokeepclone.common.BaseFragment
import com.example.mishokeepclone.data.TaskEntity
import com.example.mishokeepclone.databinding.FragmentUpdateBinding
import com.example.mishokeepclone.ui.screens.dashboard.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateFragment : BaseFragment<FragmentUpdateBinding>(FragmentUpdateBinding::inflate) {

    private val vm: UpdateViewModel by viewModels()
    val args:UpdateFragmentArgs by navArgs()

    override fun viewCreated() {

        binding.etUpdatetitle.hint = args.info.title
        binding.etUpdateDescription.hint = args.info.taskDescription
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
            binding.etUpdateDescription.text.toString())

        vm.updateTask(updatedTask)
        findNavController().navigate(UpdateFragmentDirections.actionUpdateFragmentToDashboardFragment())
    }
}