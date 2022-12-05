package com.example.mishokeepclone.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import com.example.mishokeepclone.common.Resource
import com.example.mishokeepclone.data.local.TaskEntity
import com.example.mishokeepclone.data.remote.YesOrNoDto
import com.example.mishokeepclone.data.repositoryImplementation.TasksRepositoryImplementation
import com.example.mishokeepclone.domain.YesNoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val tasksRepo: TasksRepositoryImplementation,
    private val repo:YesNoRepository
) : ViewModel() {

    fun getTasks(): Flow<List<TaskEntity>> {
        return tasksRepo.getTasks()
    }

    fun delete(task: TaskEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            tasksRepo.deleteTask(task)
        }
    }

    fun getYesNo(): Flow<Resource<YesOrNoDto>> = channelFlow {
        repo.getYesNo().collectLatest {
            when (it){
                is Resource.Loading -> {
                    send(Resource.Loading(it.loading))
                }

                is Resource.Success -> {
                    send(Resource.Success(it.data))
                }

                is Resource.Error -> {
                    send(Resource.Error(it.error))
                }
            }
        }
    }



}