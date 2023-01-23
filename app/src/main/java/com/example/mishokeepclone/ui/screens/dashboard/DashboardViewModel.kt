package com.example.mishokeepclone.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mishokeepclone.common.Resource
import com.example.mishokeepclone.data.local.TaskEntity
import com.example.mishokeepclone.data.repositoryImplementation.TasksRepositoryImplementation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val tasksRepo: TasksRepositoryImplementation,
) : ViewModel() {

    private var filteredList = listOf<TaskEntity>()

//    private val _state = MutableStateFlow<List<TaskEntity>>(mutableListOf())
//    val state = _state.asStateFlow()

    suspend fun getTasks(query:String): Flow<List<TaskEntity>> {
        return tasksRepo.getTasks(query)
    }

    fun delete(task: TaskEntity) {
        viewModelScope.launch (Dispatchers.IO) {
            tasksRepo.deleteTask(task)
        }
    }

    fun insertTask(task: TaskEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            tasksRepo.insertTask(task)
        }
    }

//    fun search(query:String) {
//        val searchedList = filteredList.filter {
//            it.priority.lowercase().contains(query.lowercase())
//        }
//        _state.value = searchedList
//    }


}