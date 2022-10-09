package com.bangkit.bahanbaku.core.domain.repository

import androidx.lifecycle.LiveData
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.data.remote.response.*
import com.bangkit.bahanbaku.core.domain.model.Profile
import io.reactivex.Flowable
import java.io.File

interface IProfileRepository {
    fun saveToken(token: String)
    fun getToken(): LiveData<String>
    fun deleteToken()
    fun getProfile(token: String): Flowable<Resource<Profile>>
    fun login(email: String, password: String): Flowable<Resource<LoginResponse>>
    fun register(
        username: String,
        email: String,
        password: String
    ): Flowable<Resource<RegisterResponse>>

    fun updateUser(
        token: String,
        username: String,
        email: String,
        password: String
    ): Flowable<Resource<UpdateProfileResponse>>

    fun uploadPicture(token: String, file: File): Flowable<Resource<UploadPictureResponse>>
    fun updateLocation(
        token: String,
        lon: Double,
        lat: Double
    ): Flowable<Resource<UpdateLocationResponse>>

//    fun getBookmarks(token: String): Flowable<Resource<List<RecipeItem>>>
//    fun addBookmark(token: String, id: String): Flowable<Resource<AddBookmarkResponse>>
//    fun deleteBookmarkByPosition(
//        token: String,
//        position: Int
//    ): Flowable<Resource<DeleteBookmarkResponse>>
//
//    fun deleteBookmark(token: String, id: String): Flowable<Resource<DeleteBookmarkResponse>>
//    fun checkIfRecipeBookmarked(token: String, id: String): Flowable<Boolean>
    fun isFirstTime(): LiveData<Boolean>
    fun setFirstTime(firstTime: Boolean)
}