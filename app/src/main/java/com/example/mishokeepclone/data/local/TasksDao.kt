package com.example.mishokeepclone.data.local

import androidx.room.*
import com.example.mishokeepclone.common.Resource
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {
    @Query("SELECT * FROM Tasks WHERE priority = :query")
    fun getSearched(query:String):Flow<List<TaskEntity>>

    @Query("SELECT * FROM Tasks")
    fun getAll():Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: TaskEntity)

    @Delete
    fun delete(user: TaskEntity)

    @Update
    fun updateTask(task: TaskEntity)

    @Query("DELETE FROM Tasks")
    fun deleteAll()

//    @Query("SELECT * FROM Tasks WHERE title LIKE :searchQuery OR taskDescription LIKE :searchQuery")
//    fun searchDatabase(searchQuery: String): Flow<List<TaskEntity>>
//


}