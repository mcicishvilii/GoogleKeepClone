package com.example.mishokeepclone.domain

import com.example.mishokeepclone.common.Resource
import com.example.mishokeepclone.data.local.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface TasksRepository {

    fun getSearched(query:String): Flow<List<TaskEntity>>

    fun getAll(): Flow<List<TaskEntity>>

    suspend fun insertTask(task: TaskEntity)

    suspend fun deleteTask(task: TaskEntity)

    suspend fun deleteAll()

    suspend fun updateTask(task: TaskEntity)

}