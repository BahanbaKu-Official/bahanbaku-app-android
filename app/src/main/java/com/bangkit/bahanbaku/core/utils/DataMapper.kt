package com.bangkit.bahanbaku.core.utils

import android.content.Context
import android.location.Address
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.data.local.entity.ProfileEntity
import com.bangkit.bahanbaku.core.data.local.entity.RecipeEntity
import com.bangkit.bahanbaku.core.data.remote.response.AddressResultItem
import com.bangkit.bahanbaku.core.data.remote.response.ProfileResult
import com.bangkit.bahanbaku.core.data.remote.response.RecipeItem
import com.bangkit.bahanbaku.core.domain.model.AddressInput
import com.bangkit.bahanbaku.core.domain.model.Profile
import com.bangkit.bahanbaku.core.domain.model.Recipe

object DataMapper {

    /* TODO: Clean up is needed
    * These functions are used to convert the data between responses, entities, and domains
    * These couple of functions are for recipe data
    */
    fun mapRecipeEntitiesToRecipeDomain(input: List<RecipeEntity>): List<Recipe> = input.map {
        Recipe(
            imageUrl = it.imageUrl,
            author = it.author,
            rating = it.rating,
            description = it.description,
            title = it.title,
            createdAt = it.createdAt,
            time = it.time,
            portion = it.portion,
            recipeId = it.recipeId,
            updatedAt = it.updatedAt,
//            deletedAt = it.deletedAt
        )
    }

    fun mapRecipeEntitiesToRecipeDomain(input: RecipeEntity): Recipe = Recipe(
        imageUrl = input.imageUrl,
        author = input.author,
        rating = input.rating,
        description = input.description,
        title = input.title,
        createdAt = input.createdAt,
        time = input.time,
        portion = input.portion,
        recipeId = input.recipeId,
        updatedAt = input.updatedAt,
//        deletedAt = input.deletedAt
    )

    fun mapRecipeDomainToRecipeEntity(input: Recipe) = RecipeEntity(
        imageUrl = input.imageUrl,
        author = input.author,
        rating = input.rating,
        description = input.description,
        title = input.title,
        createdAt = input.createdAt,
        time = input.time,
        portion = input.portion,
        recipeId = input.recipeId,
        updatedAt = input.updatedAt,
//        deletedAt = input.deletedAt
    )

    fun mapRecipeResponseToRecipeEntity(input: List<RecipeItem>): List<RecipeEntity> =
        input.map {
            RecipeEntity(
                imageUrl = it.imageUrl,
                author = it.author,
                rating = it.rating,
                description = it.description,
                title = it.title,
                createdAt = it.createdAt,
                time = it.time,
                portion = it.portion,
                recipeId = it.recipeId,
                updatedAt = it.updatedAt,
//                deletedAt = it.deletedAt
            )
        }

    fun mapRecipeResponseToRecipeEntity(input: RecipeItem) = RecipeEntity(
        imageUrl = input.imageUrl,
        author = input.author,
        rating = input.rating,
        description = input.description,
        title = input.title,
        createdAt = input.createdAt,
        time = input.time,
        portion = input.portion,
        recipeId = input.recipeId,
        updatedAt = input.updatedAt,
//        deletedAt = input.deletedAt
    )

    /*
    * These couple of functions are for recipe detail data TODO: COMPLETE
    * */

//    fun mapRecipeDetailEntitiesToRecipeDetailDomain(input: List<RecipeDetailEntity>): List<RecipeDetail> = input.map {
//        RecipeDetail(
//            imageUrl = it.imageUrl,
//            author = it.author,
//            rating = it.rating,
//            description = it.description,
//            title = it.title,
//            ingredients = it.ingredients,
//            createdAt = it.createdAt,
//            time = it.time,
//            portion = it.portion,
//            recipeId = it.recipeId,
//            updatedAt = it.updatedAt,
//            deletedAt = it.deletedAt,
//
//        )
//    }
//
//    fun mapRecipeDetailEntitiesToRecipeDetailDomain(input: RecipeDetailEntity): RecipeDetail = RecipeDetail(
//        imageUrl = input.imageUrl,
//        author = input.author,
//        rating = input.rating,
//        description = input.description,
//        title = input.title,
//        createdAt = input.createdAt,
//        time = input.time,
//        portion = input.portion,
//        recipeId = input.recipeId,
//        updatedAt = input.updatedAt,
//        deletedAt = input.deletedAt
//    )
//
//    fun mapRecipeDetailDomainToRecipeDetailEntity(input: RecipeDetail) = RecipeDetail(
//        imageUrl = input.imageUrl,
//        author = input.author,
//        rating = input.rating,
//        description = input.description,
//        title = input.title,
//        createdAt = input.createdAt,
//        time = input.time,
//        portion = input.portion,
//        recipeId = input.recipeId,
//        updatedAt = input.updatedAt,
//        deletedAt = input.deletedAt
//    )
//
//    fun mapRecipeDetailResponseToRecipeDetailEntity(input: List<RecipeDetailItem>): List<RecipeDetailEntity> =
//        input.map {
//            RecipeDetailEntity(
//                imageUrl = it.imageUrl,
//                author = it.author,
//                rating = it.rating,
//                description = it.description,
//                title = it.title,
//                createdAt = it.createdAt,
//                time = it.time,
//                portion = it.portion,
//                recipeId = it.recipeId,
//                updatedAt = it.updatedAt,
//                deletedAt = it.deletedAt
//            )
//        }
//
//    fun mapRecipeDetailResponseToRecipeDetailEntity(input: RecipeDetailItem) = RecipeDetailEntity(
//        imageUrl = input.imageUrl,
//        author = input.author,
//        rating = input.rating,
//        description = input.description,
//        title = input.title,
//        createdAt = input.createdAt,
//        time = input.time,
//        portion = input.portion,
//        recipeId = input.recipeId,
//        updatedAt = input.updatedAt,
//        deletedAt = input.deletedAt
//    )

    /*
    * These couple of functions are for profile data
    * */

    //    PROFILE data mapper
    fun mapProfileResponseToProfileEntity(input: ProfileResult) = ProfileEntity(
        firstName = input.firstName,
        lastName = input.lastName,
        createdAt = input.createdAt,
        isVerified = input.isVerified,
        profileImage = input.profileImage,
        userId = input.userId,
        email = input.email,
        updatedAt = input.updatedAt,
        phoneNumber = input.phoneNumber
    )

    fun mapProfileEntityToProfileDomain(input: ProfileEntity) = Profile(
        firstName = input.firstName,
        lastName = input.lastName,
        createdAt = input.createdAt,
        isVerified = input.isVerified,
        profileImage = input.profileImage,
        userId = input.userId,
        email = input.email,
        updatedAt = input.updatedAt,
        phoneNumber = input.phoneNumber
    )

    fun mapProfileDomainToProfileEntity(input: Profile) = ProfileEntity(
        firstName = input.firstName,
        lastName = input.lastName,
        createdAt = input.createdAt,
        isVerified = input.isVerified,
        profileImage = input.profileImage,
        userId = input.userId,
        email = input.email,
        updatedAt = input.updatedAt,
        phoneNumber = input.phoneNumber
    )

    fun mapAddressToInputAddress(context: Context, address: Address, zipCode: String, profile: Profile?) = AddressInput(
        zipCode = if (zipCode.isEmpty()) 0 else zipCode.toInt(),
        province = address.adminArea ?: "",
        city = address.subAdminArea ?: "",
        street = "${address.thoroughfare ?: ""} ${address.featureName ?: ""}",
        latitude = address.latitude,
        district = address.locality ?: "",
        label = "",
        longitude = address.longitude,
        receiverName = if (profile != null) context.getString(R.string.format_name)
            .format(profile.firstName, profile.lastName) else "",
        receiverPhoneNumber = profile?.phoneNumber ?: ""
    )
}