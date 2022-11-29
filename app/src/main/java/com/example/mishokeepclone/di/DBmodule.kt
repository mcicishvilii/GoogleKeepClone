package com.example.mishokeepclone.di

import android.content.Context
import androidx.room.Room
import com.example.mishokeepclone.data.DataBase
import com.example.mishokeepclone.data.TasksDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DBmodule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): DataBase {
        return Room.databaseBuilder(
            context,
            DataBase::class.java,"tasks_Db",
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideTasksDao(db: DataBase): TasksDao {
        return db.tasksDao
    }
}