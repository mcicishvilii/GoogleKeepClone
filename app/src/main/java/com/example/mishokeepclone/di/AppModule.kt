package com.example.mishokeepclone.di

import android.provider.SyncStateContract
import com.example.mishokeepclone.common.BaseUrl
import com.example.mishokeepclone.data.remote.YesOrNoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideYesOrNo(): YesOrNoService =
        Retrofit.Builder()
            .baseUrl(BaseUrl.YES_NO_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(YesOrNoService::class.java)

}


