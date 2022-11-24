package com.bangkit.bahanbaku.core.domain.usecase

import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.data.remote.response.GetAddressByUser
import com.bangkit.bahanbaku.core.data.remote.response.PostAddUserAddress
import com.bangkit.bahanbaku.core.data.remote.response.PostRegisterResponse
import com.bangkit.bahanbaku.core.domain.repository.IProfileRepository
import io.reactivex.Flowable
import java.io.File

class ProfileInteractor(private val profileRepository: IProfileRepository) : ProfileUseCase {
    override fun saveToken(token: String) = profileRepository.saveToken(token)

    override fun getToken() = profileRepository.getToken()

    override fun deleteToken() = profileRepository.deleteToken()

    override fun getProfile(token: String) = profileRepository.getProfile(token)

    override fun login(email: String, password: String) = profileRepository.login(email, password)

    override fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Flowable<Resource<PostRegisterResponse>> =
        profileRepository.register(firstName, lastName, email, password)

    override fun updateUser(
        token: String,
        firstName: String,
        lastName: String
    ) = profileRepository.updateUser(token, firstName, lastName)

    override fun uploadPicture(
        token: String,
        file: File
    ) = profileRepository.uploadPicture(token, file)

    override fun updateLocation(
        token: String,
        lon: Double,
        lat: Double
    ) = profileRepository.updateLocation(token, lon, lat)

    override fun getAddress(token: String): Flowable<Resource<GetAddressByUser>> =
        profileRepository.getAddress(token)

    override fun addAddress(
        token: String,
        street: String,
        district: String,
        city: String,
        province: String,
        zipCode: Int,
        label: String
    ): Flowable<Resource<PostAddUserAddress>> =
        profileRepository.addAddress(token, street, district, city, province, zipCode, label)

//    override fun getBookmarks(token: String) = profileRepository.getBookmarks(token)
//
//    override fun addBookmark(token: String, id: String) = profileRepository.addBookmark(token, id)
//
//    override fun deleteBookmarkByPosition(
//        token: String,
//        position: Int
//    ) = profileRepository.deleteBookmarkByPosition(token, position)
//
//    override fun deleteBookmark(
//        token: String,
//        id: String
//    ) = profileRepository.deleteBookmark(token, id)
//
//    override fun checkIfRecipeBookmarked(token: String, id: String) =
//        profileRepository.checkIfRecipeBookmarked(token, id)

    override fun isFirstTime() = profileRepository.isFirstTime()

    override fun setFirstTime(firstTime: Boolean) = profileRepository.setFirstTime(firstTime)
}