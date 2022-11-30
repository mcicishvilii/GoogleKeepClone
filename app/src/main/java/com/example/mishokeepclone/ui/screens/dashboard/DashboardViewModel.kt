package com.example.mishokeepclone.ui.screens.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
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

//    suspend fun getTasks(): Flow<List<TaskEntity>> {
//        return tasksRepo.getTasks()
//    }


    val readData = tasksRepo.getTasks().asLiveData()

    fun searchDatabase(searchQuery: String): LiveData<List<TaskEntity>> {
        return tasksRepo.search(searchQuery).asLiveData()
    }

    fun delete(task: TaskEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            tasksRepo.deleteTask(task)
        }
    }


}