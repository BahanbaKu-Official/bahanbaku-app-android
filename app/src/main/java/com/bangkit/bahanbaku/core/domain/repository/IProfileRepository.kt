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
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        phoneNumber: String
    ): Flowable<Resource<PostRegisterResponse>>
    fun updateUser(
        token: String,
        firstName: String,
        lastName: String
    ): Flowable<Resource<UpdateProfileResponse>>

    fun uploadPicture(token: String, file: File): Flowable<Resource<UploadPictureResponse>>
    fun updateLocation(
        token: String,
        lon: Double,
        lat: Double
    ): Flowable<Resource<UpdateLocationResponse>>
    fun isFirstTime(): LiveData<Boolean>
    fun setFirstTime(firstTime: Boolean)
    fun getAddress(token: String): Flowable<Resource<GetAddressByUser>>
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
    ): Flowable<Resource<PostAddUserAddress>>

    fun setMainAddress(addressId: String)
    fun getMainAddress(): LiveData<String>
    fun getAddressById(token: String, id: String): Flowable<Resource<GetAddressByIdResponse>>
}