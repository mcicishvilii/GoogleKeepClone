package com.example.mishokeepclone.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskEntity::class], version = 13)

abstract class DataBase: RoomDatabase() {
    abstract val tasksDao: TasksDao

}