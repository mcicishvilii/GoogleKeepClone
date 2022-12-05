package com.example.mishokeepclone.ui.screens.add_task

import androidx.lifecycle.ViewModel
import com.example.mishokeepclone.data.local.TaskEntity
import com.example.mishokeepclone.data.repositoryImplementation.TasksRepositoryImplementation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val tasksRepo: TasksRepositoryImplementation,
) : ViewModel () {

    fun insertTask(task: TaskEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            tasksRepo.insertTask(task)
        }
    }

}