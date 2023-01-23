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

    private val _state = MutableStateFlow<Resource<List<TaskEntity>>>(Resource.Loading(false))
    val state = _state.asStateFlow()

    suspend fun getTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepo.getTasks().collectLatest {
                when(it){
                    is Resource.Success -> {
                        filteredList = it.data
                        _state.value = Resource.Success(it.data)
                    }
                    is Resource.Error -> {
                        _state.value = Resource.Error("woops!")
                    }
                    is Resource.Loading -> {
                        _state.value = Resource.Loading(true)
                    }
                }
            }
        }
    }

    fun delete(task: TaskEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            tasksRepo.deleteTask(task)
        }
    }

    fun insertTask(task: TaskEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            tasksRepo.insertTask(task)
        }
    }

    fun search(query:String) {
        val searchedList = filteredList.filter {
            it.priority.lowercase().contains(query.lowercase())
        }
        _state.value = Resource.Success(searchedList)
    }


}