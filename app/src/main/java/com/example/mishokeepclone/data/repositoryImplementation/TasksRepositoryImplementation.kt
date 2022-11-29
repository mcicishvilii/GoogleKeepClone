package com.example.mishokeepclone.data.repositoryImplementation

import com.example.mishokeepclone.data.TaskEntity
import com.example.mishokeepclone.data.TasksDao
import com.example.mishokeepclone.domain.TasksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TasksRepositoryImplementation @Inject constructor(
    private val tasksDao: TasksDao
): TasksRepository {
    override fun getTasks(): Flow<List<TaskEntity>> {
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