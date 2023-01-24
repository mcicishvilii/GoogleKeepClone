package com.example.mishokeepclone.ui.screens.dashboard

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
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



    override fun viewCreated() {

        binding.personal.setBackgroundColor(Color.CYAN)
        binding.work.setBackgroundColor(Color.CYAN)
        binding.clearAll.setBackgroundColor(Color.CYAN)

        getAll()
    }

    override fun listeners() {
        delete()
        toAdd()
        filterByCategories()
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
                tasksAdapter.submitList(tasksAdapter.currentList.toList())
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


    private fun getSeached(query:String) {
        setupRecycler()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.getSearched(query).collect() {
                    tasksAdapter.submitList(it)
                    checkIfListEmpty(it)
                }
            }
        }
    }

    private fun getAll() {
        setupRecycler()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.getAll().collect() {
                    tasksAdapter.submitList(it)
                    checkIfListEmpty(it)
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


    private fun filterByCategories(){
        binding.personal.setOnClickListener {
            getSeached("Personal")
            binding.personal.setBackgroundColor(Color.GREEN)
            binding.work.setBackgroundColor(Color.RED)
            binding.clearAll.setBackgroundColor(Color.RED)
        }
        binding.work.setOnClickListener {
            getSeached("Work")
            binding.personal.setBackgroundColor(Color.RED)
            binding.work.setBackgroundColor(Color.GREEN)
            binding.clearAll.setBackgroundColor(Color.RED)
        }
        binding.clearAll.setOnClickListener {
            getAll()
            binding.personal.setBackgroundColor(Color.CYAN)
            binding.work.setBackgroundColor(Color.CYAN)
            binding.clearAll.setBackgroundColor(Color.CYAN)
        }
    }

}




