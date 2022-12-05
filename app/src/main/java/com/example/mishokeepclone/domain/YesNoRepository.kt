package com.example.mishokeepclone.domain

import com.example.mishokeepclone.common.Resource
import com.example.mishokeepclone.data.remote.YesOrNoDto
import kotlinx.coroutines.flow.Flow

interface YesNoRepository {
    suspend fun getYesNo() : Flow<Resource<YesOrNoDto>>
}