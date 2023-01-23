package com.example.mishokeepclone.domain

import com.example.mishokeepclone.common.Resource
import com.example.mishokeepclone.data.local.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface TasksRepository {

    fun getTasks(query:String): Flow<List<TaskEntity>>

    suspend fun insertTask(task: TaskEntity)

    suspend fun deleteTask(task: TaskEntity)

    suspend fun deleteAll()

    suspend fun updateTask(task: TaskEntity)

}