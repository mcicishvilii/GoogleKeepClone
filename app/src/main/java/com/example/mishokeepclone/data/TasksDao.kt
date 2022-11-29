package com.example.mishokeepclone.data

import androidx.room.*
import com.example.mishokeepclone.data.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {
    @Query("SELECT * FROM Tasks")
    fun getAll(): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: TaskEntity)

    @Delete
    fun delete(user: TaskEntity)

    @Update
    fun updateTask(task:TaskEntity)

    @Query("DELETE FROM Tasks")
    fun deleteAll()



}