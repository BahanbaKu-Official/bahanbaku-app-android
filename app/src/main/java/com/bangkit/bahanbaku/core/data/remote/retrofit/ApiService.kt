package com.bangkit.bahanbaku.core.data.remote.retrofit

import com.bangkit.bahanbaku.core.data.remote.response.*
import io.reactivex.Flowable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("user/register")
    fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Flowable<RegisterResponse>

    @FormUrlEncoded
    @POST("user/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Flowable<LoginResponse>

    @GET("user/profile")
    fun getProfile(
        @Header("Authorization") token: String
    ): Flowable<ProfileResponse>

    @FormUrlEncoded
    @PUT("user/update")
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

    @POST("user/bookmarks/{id}")
    fun addBookmark(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Flowable<AddBookmarkResponse>

    @DELETE("user/bookmarks/{id}")
    fun deleteBookmark(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Flowable<DeleteBookmarkResponse>

    @GET("/recipe")
    fun getRecipe(
        @Header("Authorization") token: String,
        @Query("search") search: String? = null,
        @Query("featured") featured: Int? = null,
        @Query("new") new: Int? = null
    ): Flowable<GetAllRecipesResponse>

    @GET("/recipe/{id}")
    fun getRecipeById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Flowable<GetRecipeByIdResponse>

    @GET("/ingredients")
    fun getIngredient(
        @Header("Authorization") token: String,
        @Query("search") search: String
    ): Flowable<IngredientResponse>

    @GET("/supplier")
    fun getSupplier(
        @Header("Authorization") token: String
    ): Flowable<SupplierResponse>

    @GET("/supplier/{id}")
    fun getSupplierById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Flowable<SupplierByIdResponse>

    @GET("/user/nearby-resto")
    fun getNearbyResto(
        @Header("Authorization") token: String,
        @Query("keyword") query: String
    ): Flowable<NearbyRestoResponse>
}