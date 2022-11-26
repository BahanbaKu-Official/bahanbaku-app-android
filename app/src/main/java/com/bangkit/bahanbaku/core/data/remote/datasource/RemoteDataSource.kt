package com.bangkit.bahanbaku.core.data.remote.datasource

import android.util.Log
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.data.remote.response.*
import com.bangkit.bahanbaku.core.data.remote.retrofit.ApiService
import com.bangkit.bahanbaku.core.domain.model.Profile
import com.bangkit.bahanbaku.core.domain.model.Recipe
import com.bangkit.bahanbaku.core.utils.DataMapper
import com.bangkit.bahanbaku.core.utils.ERROR_DEFAULT_MESSAGE
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
        val client = apiService.searchRecipe(token, query)
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
    fun getProfile(token: String): Flowable<Resource<Profile>> {
        val resultData = PublishSubject.create<Resource<Profile>>()
        val client = apiService.getProfile(token)

        val disposable = client
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val data = response.results
                val entity = DataMapper.mapProfileResponseToProfileEntity(data)
                val domain = DataMapper.mapProfileEntityToProfileDomain(entity)
                resultData.onNext(
                    if (domain.email.isNotEmpty()) Resource.Success(domain) else Resource.Error(
                        ERROR_DEFAULT_MESSAGE
                    )
                )
            }, { error ->
                resultData.onNext(Resource.Error(error.message.toString()))
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
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        phoneNumber: String,
    ): Flowable<Resource<PostRegisterResponse>> {
        val resultData = PublishSubject.create<Resource<PostRegisterResponse>>()
        val client = apiService.register(firstName, lastName, email, password, phoneNumber)
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
        firstName: String,
        lastName: String
    ): Flowable<Resource<UpdateProfileResponse>> {
        val resultData = PublishSubject.create<Resource<UpdateProfileResponse>>()
        val client = apiService.updateProfile(token, firstName, lastName)
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

    fun getFavorites(token: String): Flowable<Resource<List<FavoriteItem>>> {
        val resultData = PublishSubject.create<Resource<List<FavoriteItem>>>()
        val client = apiService.getFavorites(token)

        val disposable = client
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val data = response.results.favorite
                resultData.onNext(Resource.Success(data))
            }, { error ->
                resultData.onNext(Resource.Error(error.message.toString()))
                Log.e(TAG, error.toString())
            })

        Log.d(TAG, if (disposable.isDisposed) "Disposed" else "Not yet disposed")

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun addFavorites(token: String, id: String): Flowable<Resource<PostAddFavoriteResponse>> {
        val resultData = PublishSubject.create<Resource<PostAddFavoriteResponse>>()
        val client = apiService.addFavorites(token, id)

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

    fun deleteBookmarkByPosition(
        token: String,
        position: Int
    ): Flowable<Resource<DeleteFavoriteResponse>> {
        val resultData = PublishSubject.create<Resource<DeleteFavoriteResponse>>()
        val favoriteId = apiService.getFavorites(token)

        val disposable = favoriteId
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val a =
                    deleteFavorites(token, response.results.favorite[position].recipeId).doOnNext {
                        resultData.onNext(it)
                    }
            }, { error ->
                resultData.onNext(Resource.Error(error.message.toString()))
                Log.e(TAG, error.toString())
            })

        Log.d(TAG, if (disposable.isDisposed) "Disposed" else "Not yet disposed")

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }


    fun deleteFavorites(token: String, id: String): Flowable<Resource<DeleteFavoriteResponse>> {
        val resultData = PublishSubject.create<Resource<DeleteFavoriteResponse>>()
        val client = apiService.deleteFavorites(token, id)

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

    fun checkIfRecipeBookmarked(token: String, id: String): Flowable<Boolean> {
        val resultData = PublishSubject.create<Boolean>()
        val client = apiService.getFavorites(token)

        val disposable = client
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                var isAvailable = false
                response.results.favorite.forEach {
                    if (it.recipeId == id) {
                        isAvailable = true
                    }
                }

                resultData.onNext(isAvailable)
            }, { error ->
                resultData.onNext(false)
                Log.e(TAG, error.toString())
            })

        Log.d(TAG, if (disposable.isDisposed) "Disposed" else "Not yet disposed")

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getAddress(token: String): Flowable<Resource<GetAddressByUser>> {
        val resultData = PublishSubject.create<Resource<GetAddressByUser>>()
        val client = apiService.getUserAddress(token)

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

    fun addAddress(
        token: String,
        street: String,
        district: String,
        city: String,
        province: String,
        zipCode: Int,
        label: String,
        receiverName: String,
        receiverNumber: String,
    ): Flowable<Resource<PostAddUserAddress>> {
        val resultData = PublishSubject.create<Resource<PostAddUserAddress>>()
        val client =
            apiService.addUserAddress(token, street, district, city, province, zipCode, label, receiverName, receiverNumber)

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