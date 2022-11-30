package com.example.mishokeepclone.domain

import com.example.mishokeepclone.data.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TasksRepository {

    fun getTasks(): Flow<List<TaskEntity>>

    suspend fun insertTask(task: TaskEntity)

    suspend fun deleteTask(task: TaskEntity)

    suspend fun deleteAll()

    suspend fun updateTask(task:TaskEntity)

    fun search(query:String):Flow<List<TaskEntity>>
}