package com.example.mishokeepclone.di

import com.example.mishokeepclone.data.repositoryImplementation.TasksRepositoryImplementation
import com.example.mishokeepclone.domain.TasksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModuleDb {
    @Binds
    @Singleton
    abstract fun provideTasksRepo(repoImpl: TasksRepositoryImplementation): TasksRepository
}