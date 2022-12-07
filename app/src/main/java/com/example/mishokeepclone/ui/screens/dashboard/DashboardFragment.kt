package com.example.mishokeepclone.ui.screens.dashboard

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mishokeepclone.common.BaseFragment
import com.example.mishokeepclone.common.Resource
import com.example.mishokeepclone.data.local.TaskEntity
import com.example.mishokeepclone.databinding.FragmentDashboardBinding
import com.example.mishokeepclone.ui.adapters.TasksAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment :
    BaseFragment<FragmentDashboardBinding>(FragmentDashboardBinding::inflate) {

    private val tasksAdapter: TasksAdapter by lazy { TasksAdapter() }

    private val vm: DashboardViewModel by viewModels()


    override fun viewCreated() {
        getTasks()
    }

    override fun listeners() {
        delete()
        toAdd()
        getYesNoAnswer()
    }

    private fun toAdd() {
        binding.button.setOnClickListener {
            findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToAddTaskFragment())
        }
    }

    private fun checkIfListEmpty(list: List<TaskEntity>) {
        if (list.isNotEmpty()) binding.tvTasksPresent.visibility = View.GONE
        if (list.isEmpty()) binding.tvTasksPresent.visibility = View.VISIBLE
    }

    private fun delete() {
        tasksAdapter.apply {
            setOnItemClickListener { taskEntity, _ ->
                vm.delete(taskEntity)
            }
        }
    }

    private fun getTasks() {
        setupRecycler()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.getTasks().collect() {
                    tasksAdapter.submitList(it)
                    checkIfListEmpty(it)
                }
            }
        }
    }

    private fun getYesNoAnswer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.getYesNo().collect() { answer ->
                    when (answer) {
                        is Resource.Error -> {

                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            binding.tvYesNo.text = answer.data.answer
                            Glide.with(binding.ivYesNo)
                                .load(answer.data?.image)
                                .into(binding.ivYesNo)
                        }
                    }
                }
            }
        }
    }

    private fun setupRecycler() {
        binding.rvTasks.apply {
            adapter = tasksAdapter
            layoutManager =
                LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
        }
    }


}




