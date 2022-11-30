package com.example.mishokeepclone.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import com.example.mishokeepclone.common.Resource
import com.example.mishokeepclone.data.TaskEntity
import com.example.mishokeepclone.data.repositoryImplementation.TasksRepositoryImplementation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val tasksRepo: TasksRepositoryImplementation,
) : ViewModel() {

    private val _state = MutableStateFlow<Resource<List<TaskEntity>>>(Resource.Loading(false))
    val state = _state.asStateFlow()

    private var filteredList = listOf<TaskEntity>()

    suspend fun getTasks(): Flow<List<TaskEntity>> {
        return tasksRepo.getTasks()
    }

    fun delete(task: TaskEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            tasksRepo.deleteTask(task)
        }
    }

    fun search(query:String) {
        val searchedList = filteredList.filter {
            it.priority.lowercase().contains(query.lowercase())
        }
        _state.value = Resource.Success(searchedList)
    }

}