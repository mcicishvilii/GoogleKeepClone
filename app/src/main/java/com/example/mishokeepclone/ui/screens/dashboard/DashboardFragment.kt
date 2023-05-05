package com.example.mishokeepclone.ui.screens.dashboard

import android.animation.ObjectAnimator
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.GridLayout
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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




        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterLandmarks(newText)
                return true
            }
        })

        setupInitialRecycler()

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

    private fun filterLandmarks(query: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.getAll().collect() { tasks ->
                    val filteredTasks = tasks.filter {
                        it.title.contains(query, true)}
                    tasksAdapter.submitList(filteredTasks)
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

    private fun setupInitialRecycler() {
        binding.rvTasks.apply {
            layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }
    private fun setupRecycler() {
        binding.rvTasks.apply {

            val animator = ObjectAnimator.ofFloat(this, "alpha", 0f, 1f)
            animator.duration = 500

            // Set up the layout change listener
            val layoutChangeListener = object : View.OnLayoutChangeListener {
                override fun onLayoutChange(
                    v: View?,
                    left: Int,
                    top: Int,
                    right: Int,
                    bottom: Int,
                    oldLeft: Int,
                    oldTop: Int,
                    oldRight: Int,
                    oldBottom: Int
                ) {
                    // Run the animator
                    animator.start()
                    // Remove the listener after the animation is finished
                    removeOnLayoutChangeListener(this)
                }
            }


            binding.switcher.setOnCheckedChangeListener { _, isChecked ->
                binding.rvTasks.layoutManager = if (isChecked) {
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                } else {
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }
                addOnLayoutChangeListener(layoutChangeListener)
            }


            adapter = tasksAdapter

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




