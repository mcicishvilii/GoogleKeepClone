package com.example.mishokeepclone.ui.screens.dashboard


import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mishokeepclone.R
import com.example.mishokeepclone.common.BaseFragment
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
        tasksAdapter.apply {
            setOnItemClickListener{taskEntity, i ->
                vm.delete(taskEntity)
            }
        }


        binding.button.setOnClickListener{
            findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToAddTaskFragment())
        }
    }

    private fun getTasks() {
        setupRecycler()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.getTasks().collect() {
                    tasksAdapter.submitList(it)
                }
            }
        }
    }

    private fun setupRecycler() {
        binding.rvTasks.apply {
            adapter = tasksAdapter
            layoutManager =
                GridLayoutManager(
                    requireContext(),
                    2,
                    GridLayoutManager.VERTICAL,
                    false
                )
        }
    }


}