package com.example.mishokeepclone.ui.screens.dashboard

import android.R
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mishokeepclone.common.BaseFragment
import com.example.mishokeepclone.common.Resource
import com.example.mishokeepclone.data.local.TaskEntity
import com.example.mishokeepclone.databinding.FragmentDashboardBinding
import com.example.mishokeepclone.ui.adapters.TasksAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class DashboardFragment :
    BaseFragment<FragmentDashboardBinding>(FragmentDashboardBinding::inflate) {

    private val tasksAdapter: TasksAdapter by lazy { TasksAdapter() }
    private val vm: DashboardViewModel by viewModels()

    private var filteredList = mutableListOf<TaskEntity>()

    override fun viewCreated() {

        getTasks()
    }

    override fun listeners() {
        delete()
        toAdd()
        search()
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
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            UP or DOWN,
            LEFT or RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val task = tasksAdapter.currentList[position]
                vm.delete(task)
                Snackbar.make(view!!, "Deleted task", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        vm.insertTask(task)
                    }
                    show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvTasks)
        }
    }

    private fun getTasks() {

        setupRecycler()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.getTasks()
                vm.state.collect() {
                    when (it) {
                        is Resource.Error -> {

                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            checkIfListEmpty(it.data)
                            tasksAdapter.submitList(it.data)
                            filteredList = it.data.toMutableList()
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


    private fun search() {
        binding.search.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) {
                vm.search(text.toString())
            }
            else{
                tasksAdapter.submitList(filteredList)
            }
        }
    }
}




