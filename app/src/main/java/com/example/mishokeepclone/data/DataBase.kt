package com.example.mishokeepclone.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskEntity::class], version = 9)

abstract class DataBase: RoomDatabase() {
    abstract val tasksDao: TasksDao

}