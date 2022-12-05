package com.example.mishokeepclone.ui.screens.update

import androidx.lifecycle.ViewModel
import com.example.mishokeepclone.data.local.TaskEntity
import com.example.mishokeepclone.data.repositoryImplementation.TasksRepositoryImplementation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class UpdateViewModel @Inject constructor(
    private val tasksRepo: TasksRepositoryImplementation,
) : ViewModel () {

    fun updateTask(task: TaskEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            tasksRepo.updateTask(task)
        }
    }

}