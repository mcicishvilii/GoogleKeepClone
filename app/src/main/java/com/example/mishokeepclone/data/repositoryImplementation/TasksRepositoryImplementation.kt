package com.example.mishokeepclone.data.repositoryImplementation

import com.example.mishokeepclone.common.Resource
import com.example.mishokeepclone.data.local.TaskEntity
import com.example.mishokeepclone.data.local.TasksDao
import com.example.mishokeepclone.domain.TasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class TasksRepositoryImplementation @Inject constructor(
    private val tasksDao: TasksDao,
) : TasksRepository {

    override fun getSearched(query:String): Flow<List<TaskEntity>> {
        return tasksDao.getSearched(query)
    }

    override fun getAll(): Flow<List<TaskEntity>> {
        return tasksDao.getAll()
    }

    override suspend fun insertTask(task: TaskEntity) {
        return tasksDao.insert(task)
    }

    override suspend fun deleteTask(task: TaskEntity) {
        return tasksDao.delete(task)
    }

    override suspend fun deleteAll() {
        return tasksDao.deleteAll()
    }

    override suspend fun updateTask(task: TaskEntity) {
        return tasksDao.updateTask(task)
    }

}