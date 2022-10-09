package com.bangkit.bahanbaku.core.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.data.remote.response.NearbyRestoResponse
import com.bangkit.bahanbaku.core.data.remote.response.SnapFoodResponse
import com.bangkit.bahanbaku.core.data.remote.retrofit.ApiService
import com.bangkit.bahanbaku.core.data.remote.retrofit.ApiServiceML
import java.io.File
import javax.inject.Inject

class FoodRepository @Inject constructor(
    private val apiServiceML: ApiServiceML,
    private val apiService: ApiService
//    private val database: FoodDatabase
) {
    fun postSnapFood(token: String, file: File): LiveData<Resource<SnapFoodResponse>> = liveData {
//        emit(Resource.Loading)
//
//        val imageMediaType = "image".toMediaTypeOrNull()
//        val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
//            "gambar",
//            file.name,
//            file.asRequestBody(imageMediaType)
//        )
//
//        try {
//            val response = apiServiceML.uploadSnapFood(token, imageMultiPart)
//            emit(Resource.Success(response))
//        } catch (e: Exception) {
//            e.printStackTrace()
//            emit(Resource.Error(e.message.toString()))
//        }
    }

    fun getNearbyResto(token: String, query: String): LiveData<Resource<NearbyRestoResponse>> =
        liveData {
//            emit(Resource.Loading)
//            try {
//                val response = apiService.getNearbyResto(token, query)
//                emit(Resource.Success(response))
//            } catch (e: Exception) {
//                emit(Resource.Error(e.message.toString()))
//            }
        }
}