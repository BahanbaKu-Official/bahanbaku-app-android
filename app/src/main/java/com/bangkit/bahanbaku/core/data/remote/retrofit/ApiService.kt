package com.bangkit.bahanbaku.core.data.remote.retrofit

import com.bangkit.bahanbaku.core.data.remote.response.*
import io.reactivex.Flowable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Flowable<PostRegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Flowable<LoginResponse>

    @GET("users/profile")
    fun getProfile(
        @Header("Authorization") token: String
    ): Flowable<GetProfileResponse>

    @FormUrlEncoded
    @PUT("users/update/profile")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Flowable<UpdateProfileResponse>

    @PUT("user/update-location")
    fun updateLocation(
        @Header("Authorization") token: String,
        @Body location: RequestBody
    ): Flowable<UpdateLocationResponse>

    @Multipart
    @POST("user/upload-picture")
    fun uploadPicture(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): Flowable<UploadPictureResponse>

    @GET("favorites")
    fun getFavorites(
        @Header("Authorization") token: String,
    ): Flowable<GetFavoriteResponse>

    @POST("favorites/{id}")
    fun addFavorites(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Flowable<PostAddFavoriteResponse>

    @DELETE("favorites/{id}")
    fun deleteFavorites(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Flowable<DeleteFavoriteResponse>

    @GET("/recipes")
    fun getRecipe(
        @Header("Authorization") token: String
    ): Flowable<GetAllRecipesResponse>

    @GET("recipes/search")
    fun searchRecipe(
        @Header("Authorization") token: String,
        @Query("title") name: String? = ""
    ): Flowable<GetAllRecipesResponse>

    @GET("/recipes/{id}")
    fun getRecipeById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Flowable<GetRecipeByIdResponse>

    @GET("/tags/search")
    fun getRecipeByTag(
        @Header("Authorization") token: String,
        @Query("tag") tag: String = ""
    ): Flowable<GetRecipesByTagResponse>
}