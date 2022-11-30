package com.example.mishokeepclone.ui.screens.dashboard


import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mishokeepclone.R
import com.example.mishokeepclone.common.BaseFragment
import com.example.mishokeepclone.common.Resource
import com.example.mishokeepclone.data.TaskEntity
import com.example.mishokeepclone.databinding.FragmentDashboardBinding
import com.example.mishokeepclone.ui.adapters.CategoriesAdapter
import com.example.mishokeepclone.ui.adapters.TasksAdapter
import com.example.mishokeepclone.ui.model.addCat
import com.example.mishokeepclone.ui.model.cats
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment :
    BaseFragment<FragmentDashboardBinding>(FragmentDashboardBinding::inflate) {

    private val tasksAdapter: TasksAdapter by lazy { TasksAdapter() }
    private val categoryAdapter: CategoriesAdapter by lazy { CategoriesAdapter() }
    private val vm: DashboardViewModel by viewModels()
    private var filteredList = mutableListOf<TaskEntity>()

    override fun viewCreated() {
//        getTasks()
        getCategories()
        observe()
        filter()
    }

    override fun listeners() {
        delete()
        toAdd()


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
            setOnItemClickListener { taskEntity, i ->
                vm.delete(taskEntity)
            }
        }
    }

    private fun observe(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.state.collectLatest{
                    when (it) {
                        is Resource.Error -> {

                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            tasksAdapter.submitList(it.data)
                            filteredList = it.data.toMutableList()
                        }
                    }
                }
            }
        }
    }

    private fun filter(){
        categoryAdapter.apply {
            setOnItemClickListener{item,_ ->
                if (!item.cat.isNullOrEmpty()){
                    vm.search(item.cat)
                }else{
                    tasksAdapter.submitList(filteredList)
                }
            }
        }
    }


//    private fun getTasks() {
//        setupRecycler()
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                vm.getTasks().collect() {
//                    tasksAdapter.submitList(it)
//                    checkIfListEmpty(it)
//                    Log.d("misho", it.size.toString())
//                }
//            }
//        }
//    }

    private fun getCategories() {
        addCat()
        setupCatRecycler()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    categoryAdapter.submitList(cats)
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

    private fun setupCatRecycler() {
        binding.rvCategories.apply {
            adapter = categoryAdapter
            layoutManager =
                LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
        }
    }


}