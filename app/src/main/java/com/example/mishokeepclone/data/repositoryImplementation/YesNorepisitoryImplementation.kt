package com.example.mishokeepclone.data.repositoryImplementation

import com.example.mishokeepclone.common.Resource
import com.example.mishokeepclone.data.remote.YesOrNoDto
import com.example.mishokeepclone.data.remote.YesOrNoService
import com.example.mishokeepclone.domain.YesNoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class YesNorepisitoryImplementation @Inject constructor(
    private val service: YesOrNoService
) : YesNoRepository {
    override suspend fun getYesNo(): Flow<Resource<YesOrNoDto>> = flow {
        try {
            emit(Resource.Loading(true))
            val response = service.getYesOrNo()
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()!!))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "unexpected"))
        }
    }
}