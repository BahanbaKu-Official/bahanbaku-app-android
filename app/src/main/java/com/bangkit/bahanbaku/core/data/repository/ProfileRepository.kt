package com.bangkit.bahanbaku.core.data.repository

import androidx.lifecycle.asLiveData
import com.bangkit.bahanbaku.core.data.NetworkBoundResource
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.data.local.datasource.LocalDataSource
import com.bangkit.bahanbaku.core.data.local.datastore.UserPreferences
import com.bangkit.bahanbaku.core.data.remote.ApiResponse
import com.bangkit.bahanbaku.core.data.remote.datasource.RemoteDataSource
import com.bangkit.bahanbaku.core.data.remote.response.*
import com.bangkit.bahanbaku.core.domain.model.Profile
import com.bangkit.bahanbaku.core.domain.repository.IProfileRepository
import com.bangkit.bahanbaku.core.utils.AppExecutors
import com.bangkit.bahanbaku.core.utils.DataMapper
import io.reactivex.Flowable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import kotlin.coroutines.CoroutineContext

class ProfileRepository (
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val userPreferences: UserPreferences,
    private val appExecutors: AppExecutors
) : IProfileRepository, CoroutineScope {
    override fun saveToken(token: String) {
        launch(Dispatchers.IO) {
            userPreferences.saveToken(token)
        }
    }

    override fun getToken() = userPreferences.getToken().asLiveData(coroutineContext)

    override fun deleteToken() {
        launch(Dispatchers.IO) {
            userPreferences.deleteToken()
        }
    }

    override fun getProfile(token: String): Flowable<Resource<Profile>> =
        object : NetworkBoundResource<Profile, ProfileItem>(appExecutors) {
            override fun createCall(): Flowable<ApiResponse<ProfileItem>> {
                return remoteDataSource.getProfile(token)
            }

            override fun loadFromDB(): Flowable<Profile> {
                return localDataSource.getProfile().map {
                    DataMapper.mapProfileEntityToProfileDomain(it[0])
                }
            }

            override fun shouldFetch(data: Profile?): Boolean = data == null

            override fun saveCallResult(data: ProfileItem) {
                localDataSource.deleteProfile()
                localDataSource.insertProfile(DataMapper.mapProfileResponseToProfileEntity(data))
            }

        }.asFlowable()

    override fun login(email: String, password: String): Flowable<Resource<LoginResponse>> =
        remoteDataSource.login(email, password)

    override fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Flowable<Resource<PostRegisterResponse>> = remoteDataSource.register(firstName, lastName, email, password)

    override fun updateUser(
        token: String,
        username: String,
        email: String,
        password: String
    ): Flowable<Resource<UpdateProfileResponse>> = remoteDataSource.updateUser(token, username, email, password)

    override fun uploadPicture(
        token: String,
        file: File
    ): Flowable<Resource<UploadPictureResponse>> = remoteDataSource.uploadPicture(token, file)

    override fun updateLocation(
        token: String,
        lon: Double,
        lat: Double
    ): Flowable<Resource<UpdateLocationResponse>> = remoteDataSource.updateLocation(token, lon, lat)

//    override fun getBookmarks(token: String): Flowable<Resource<List<RecipeItem>>> = liveData {
////        TODO: IMPLEMENT NetworkBoundResource
//    }
//
//    override fun addBookmark(token: String, id: String): Flowable<Resource<AddBookmarkResponse>> = liveData {
////        TODO: IMPLEMENT NetworkBoundResource
//    }
//
//    override fun deleteBookmarkByPosition(
//        token: String,
//        position: Int
//    ): Flowable<Resource<DeleteBookmarkResponse>> = liveData {
////        TODO: IMPLEMENT NetworkBoundResource
//    }
//
//    override fun deleteBookmark(token: String, id: String): Flowable<Resource<DeleteBookmarkResponse>> =
//        liveData {
////        TODO: IMPLEMENT NetworkBoundResource
//        }
//
//    override fun checkIfRecipeBookmarked(token: String, id: String): Flowable<Boolean> = liveData {
////        TODO: IMPLEMENT NetworkBoundResource
//    }

    override fun isFirstTime() = userPreferences.isFirstTime().asLiveData(coroutineContext)

    override fun setFirstTime(firstTime: Boolean) {
        launch(Dispatchers.IO) {
            userPreferences.setFirstTime(firstTime)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
}