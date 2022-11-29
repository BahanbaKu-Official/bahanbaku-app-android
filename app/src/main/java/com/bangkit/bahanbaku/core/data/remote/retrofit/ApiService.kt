package com.bangkit.bahanbaku.core.data.remote.retrofit

import com.bangkit.bahanbaku.core.data.remote.response.*
import com.bangkit.bahanbaku.core.domain.model.ProductsData
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
        @Field("password") password: String,
        @Field("phoneNumber") phoneNumber: String,
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
    @POST("users/update/profile")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String
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

    @GET("users/address")
    fun getUserAddress(
        @Header("Authorization") token: String
    ): Flowable<GetAddressByUser>

    @FormUrlEncoded
    @POST("users/address")
    fun addUserAddress(
        @Header("Authorization") token: String,
        @Field("street") street: String,
        @Field("district") district: String,
        @Field("city") city: String,
        @Field("province") province: String,
        @Field("zipCode") zipCode: Int,
        @Field("label") label: String,
        @Field("receiverName") receiverName: String,
        @Field("receiverPhoneNumber") receiverNumber: String,
    ): Flowable<PostAddUserAddress>

    @GET("/transaction/payment-method")
    fun getPaymentMethod(
        @Header("Authorization") token: String
    ): Flowable<GetPaymentMethodResponse>

    @GET("users/address/{id}")
    fun getUserAddressById(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Flowable<GetAddressByIdResponse>

    @POST("direct-pay/{id}")
    fun createDirectPayment(
        @Header("Authorization") token: String,
        @Body productsData: ProductsData
    ): Flowable<PostCreateDirectPaymentResponse>

    @Multipart
    @POST("direct-pay/submit-payment/{id}")
    fun submitPaymentProof(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Path("id") id: String
    ): Flowable<PostSubmitProofResponse>

    @GET("direct-pay/payment-method")
    fun getDirectPaymentInfo(
        @Header("Authorization") token: String
    ): Flowable<GetDirectPaymentInfoResponse>

    @GET("direct-pay")
    fun getDirectOrderHistory(
        @Header("Authorization") token: String
    ): Flowable<GetOrderHistoryResponse>
}

