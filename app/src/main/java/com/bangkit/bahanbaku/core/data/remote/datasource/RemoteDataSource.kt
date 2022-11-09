package com.bangkit.bahanbaku.core.data.remote.datasource

import android.util.Log
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.data.remote.ApiResponse
import com.bangkit.bahanbaku.core.data.remote.response.*
import com.bangkit.bahanbaku.core.data.remote.retrofit.ApiService
import com.bangkit.bahanbaku.core.domain.model.Recipe
import com.bangkit.bahanbaku.core.utils.DataMapper
import com.bangkit.bahanbaku.core.utils.ERROR_NULL_VALUE
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File

class RemoteDataSource private constructor(private val apiService: ApiService) {

    //    RECIPES data sources
    fun getNewRecipes(token: String): Flowable<Resource<List<Recipe>>> {
        val resultData = PublishSubject.create<Resource<List<Recipe>>>()
        val client = apiService.getRecipe(token)

        val disposable = client
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val data = response.results
                val entity = DataMapper.mapRecipeResponseToRecipeEntity(data)
                val domain = DataMapper.mapRecipeEntitiesToRecipeDomain(entity)
                resultData.onNext(
                    if (domain.isNotEmpty()) Resource.Success(domain) else Resource.Error(
                        ERROR_NULL_VALUE
                    )
                )
            }, { error ->
                resultData.onNext(Resource.Error(error.message.toString()))
                Log.e(TAG, error.toString())
            })

        Log.d(TAG, if (disposable.isDisposed) "Disposed" else "Not yet disposed")

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getRecipesByTag(token: String, tag: String): Flowable<Resource<List<Recipe>>> {
        val resultData = PublishSubject.create<Resource<List<Recipe>>>()
        val client = apiService.getRecipeByTag(token, tag)

        val disposable = client
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val data = response.results
                val entity = DataMapper.mapRecipeResponseToRecipeEntity(data.recipes)
                val domain = DataMapper.mapRecipeEntitiesToRecipeDomain(entity)
                resultData.onNext(
                    if (domain.isNotEmpty()) Resource.Success(domain) else Resource.Error(
                        ERROR_NULL_VALUE
                    )
                )
            }, { error ->
                resultData.onNext(Resource.Error(error.message.toString()))
                Log.e(TAG, error.toString())
            })

        Log.d(TAG, if (disposable.isDisposed) "Disposed" else "Not yet disposed")

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun searchRecipe(token: String, query: String): Flowable<Resource<List<Recipe>>> {
        val resultData = PublishSubject.create<Resource<List<Recipe>>>()
        val client = apiService.getRecipe(token, query)
        resultData.onNext(Resource.Loading(null))

        val disposable = client
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val data = response.results
                if (data.isNotEmpty()) {
                    val entityData = DataMapper.mapRecipeResponseToRecipeEntity(data)
                    val domainData = DataMapper.mapRecipeEntitiesToRecipeDomain(entityData)
                    resultData.onNext(Resource.Success(domainData))
                } else {
                    resultData.onNext(Resource.Error(ERROR_NULL_VALUE))
                }
            }, { error ->
                resultData.onNext(Resource.Error(error.message.toString()))
                Log.e(TAG, error.toString())
            })

        Log.d(TAG, if (disposable.isDisposed) "Disposed" else "Not yet disposed")

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getRecipeById(token: String, id: String): Flowable<Resource<RecipeDetailItem>> {
        val resultData = PublishSubject.create<Resource<RecipeDetailItem>>()
        val client = apiService.getRecipeById(token, id)

        val disposable = client
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val data = response.results
                resultData.onNext(
                    if (data.title.isNotEmpty()) Resource.Success(data) else Resource.Error(
                        ERROR_NULL_VALUE
                    )
                )
            }, { error ->
                resultData.onNext(Resource.Error(error.message.toString()))
                Log.e(TAG, error.toString())
            })

        Log.d(TAG, if (disposable.isDisposed) "Disposed" else "Not yet disposed")

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    //    PROFILE data sources
    fun getProfile(token: String): Flowable<ApiResponse<ProfileItem>> {
        val resultData = PublishSubject.create<ApiResponse<ProfileItem>>()
        val client = apiService.getProfile(token)

        val disposable = client
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val data = response.results
                resultData.onNext(if (data.username.isNotEmpty()) ApiResponse.Success(data) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e(TAG, error.toString())
            })

        Log.d(TAG, if (disposable.isDisposed) "Disposed" else "Not yet disposed")

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun login(email: String, password: String): Flowable<Resource<LoginResponse>> {
        val resultData = PublishSubject.create<Resource<LoginResponse>>()
        val client = apiService.login(email, password)
        resultData.onNext(Resource.Loading(null))

        val disposable = client
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                resultData.onNext(
                    if (response.token.isNotEmpty()) Resource.Success(response) else Resource.Error(
                        ERROR_NULL_VALUE
                    )
                )
            }, { error ->
                resultData.onNext(Resource.Error(error.message.toString()))
                Log.e(TAG, error.toString())
            })

        Log.d(TAG, if (disposable.isDisposed) "Disposed" else "Not yet disposed")

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun register(
        username: String,
        email: String,
        password: String
    ): Flowable<Resource<RegisterResponse>> {
        val resultData = PublishSubject.create<Resource<RegisterResponse>>()
        val client = apiService.register(username, email, password)
        resultData.onNext(Resource.Loading(null))

        val disposable = client
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                resultData.onNext(Resource.Success(response))
            }, { error ->
                resultData.onNext(Resource.Error(error.message.toString()))
                Log.e(TAG, error.toString())
            })

        Log.d(TAG, if (disposable.isDisposed) "Disposed" else "Not yet disposed")

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun updateUser(
        token: String,
        username: String,
        email: String,
        password: String
    ): Flowable<Resource<UpdateProfileResponse>> {
        val resultData = PublishSubject.create<Resource<UpdateProfileResponse>>()
        val client = apiService.updateProfile(token, username, email, password)
        resultData.onNext(Resource.Loading(null))

        val disposable = client
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                resultData.onNext(Resource.Success(response))
            }, { error ->
                resultData.onNext(Resource.Error(error.message.toString()))
                Log.e(TAG, error.toString())
            })

        Log.d(TAG, if (disposable.isDisposed) "Disposed" else "Not yet disposed")

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun uploadPicture(
        token: String,
        file: File
    ): Flowable<Resource<UploadPictureResponse>> {
        val mediaType = "image".toMediaTypeOrNull()
        val multipartBody =
            MultipartBody.Part.createFormData("image", file.name, file.asRequestBody(mediaType))

        val resultData = PublishSubject.create<Resource<UploadPictureResponse>>()
        val client = apiService.uploadPicture(token, multipartBody)
        resultData.onNext(Resource.Loading(null))

        val disposable = client
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                resultData.onNext(Resource.Success(response))
            }, { error ->
                resultData.onNext(Resource.Error(error.message.toString()))
                Log.e(TAG, error.toString())
            })

        Log.d(TAG, if (disposable.isDisposed) "Disposed" else "Not yet disposed")

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun updateLocation(
        token: String,
        lon: Double,
        lat: Double
    ): Flowable<Resource<UpdateLocationResponse>> {
        val location = JSONObject()
        location.put("lat", lat)
        location.put("lng", lon)

        val bodyObject = JSONObject()
        bodyObject.put("location", location)

        val requestBody = bodyObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val resultData = PublishSubject.create<Resource<UpdateLocationResponse>>()
        val client = apiService.updateLocation(token, requestBody)
        resultData.onNext(Resource.Loading(null))

        val disposable = client
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                resultData.onNext(Resource.Success(response))
            }, { error ->
                resultData.onNext(Resource.Error(error.message.toString()))
                Log.e(TAG, error.toString())
            })

        Log.d(TAG, if (disposable.isDisposed) "Disposed" else "Not yet disposed")

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

//    //    Feel like it's not perfect yet
//    fun getBookmarks(token: String): LiveData<ApiResponse<List<RecipeItem>>> {
//        val resultData = PublishSubject.create<ApiResponse<List<RecipeItem>>>()
//        val client = apiService.getProfile(token)
//
//        val disposable = client
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .take(1)
//            .subscribe({ response ->
//                val data = response.results.bookmarks.map {
//                    getRecipeById(token, it)
//                }
//                resultData.onNext(ApiResponse.Success(data))
//            }, { error ->
//
//            })
//        try {
//            val profile = apiService.getProfile(token).results
//            val recipeList = mutableListOf<Recipe>()
//            for (id in profile.bookmarks) {
//                val entity = DataMapper.mapRecipeResponseToRecipeEntity(
//                    apiService.getRecipeById(
//                        token,
//                        id
//                    ).results
//                )
//                val domain = DataMapper.mapRecipeEntitiesToRecipeDomain(entity)
//                recipeList.add(domain)
//            }
//            emit(ApiResponse.Success(recipeList))
//        } catch (e: Exception) {
//            emit(ApiResponse.Error(e.message.toString()))
//        }
//    }
//
//    fun addBookmark(token: String, id: String): LiveData<ApiResponse<AddBookmarkResponse>> =
//        liveData {
//            try {
//                val response = apiService.addBookmark(token, id)
//                emit(ApiResponse.Success(response))
//            } catch (e: Exception) {
//                emit(ApiResponse.Error(e.message.toString()))
//            }
//        }
//
//    fun deleteBookmarkByPosition(
//        token: String,
//        position: Int
//    ): LiveData<ApiResponse<DeleteBookmarkResponse>> = liveData {
//        try {
//            val bookmarkId = apiService.getProfile(token).results.bookmarks[position]
//            val result = apiService.deleteBookmark(token, bookmarkId)
//            emit(ApiResponse.Success(result))
//        } catch (e: Exception) {
//            emit(ApiResponse.Error(e.message.toString()))
//        }
//    }
//
//    fun deleteBookmark(token: String, id: String): LiveData<ApiResponse<DeleteBookmarkResponse>> =
//        liveData {
//            try {
//                val response = apiService.deleteBookmark(token, id)
//                emit(ApiResponse.Success(response))
//            } catch (e: Exception) {
//                emit(ApiResponse.Error(e.message.toString()))
//            }
//        }
//
//    fun checkIfRecipeBookmarked(token: String, id: String): LiveData<Boolean> = liveData {
//        val bookmarks = apiService.getProfile(token).results.bookmarks
//        var isBookmarked = false
//        if (!bookmarks.isNullOrEmpty()) {
//            isBookmarked = (id in bookmarks)
//        }
//        emit(isBookmarked)
//    }

    companion object {
        private const val TAG = "REMOTE_DATA_SOURCE"

        @Volatile
        private var INSTANCE: RemoteDataSource? = null

        fun getInstance(apiService: ApiService): RemoteDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RemoteDataSource(apiService)
            }
    }
}