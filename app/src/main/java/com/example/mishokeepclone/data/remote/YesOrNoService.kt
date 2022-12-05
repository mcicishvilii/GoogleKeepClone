package com.example.mishokeepclone.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface YesOrNoService {
    @GET("api")
    suspend fun getYesOrNo():Response<YesOrNoDto>
}