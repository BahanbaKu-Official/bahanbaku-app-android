package com.bangkit.bahanbaku.core.utils

import com.bangkit.bahanbaku.core.data.local.entity.ProfileEntity
import com.bangkit.bahanbaku.core.data.local.entity.RecipeEntity
import com.bangkit.bahanbaku.core.data.remote.response.ProfileItem
import com.bangkit.bahanbaku.core.data.remote.response.RecipeItem
import com.bangkit.bahanbaku.core.domain.model.Profile
import com.bangkit.bahanbaku.core.domain.model.Recipe

object DataMapper {

    //    RECIPE data mapper
    fun mapRecipeEntitiesToRecipeDomain(input: List<RecipeEntity>): List<Recipe> = input.map {
        Recipe(
            image = it.image,
            author = it.author,
            rating = it.rating,
            description = it.description,
            title = it.title,
            steps = it.steps,
            createdAt = it.createdAt,
            times = it.times,
            servings = it.servings,
            ingredients = it.ingredients,
            totalViews = it.totalViews,
            id = it.id,
            updatedAt = it.updatedAt
        )
    }

    fun mapRecipeEntitiesToRecipeDomain(input: RecipeEntity): Recipe = Recipe(
        image = input.image,
        author = input.author,
        rating = input.rating,
        description = input.description,
        title = input.title,
        steps = input.steps,
        createdAt = input.createdAt,
        times = input.times,
        servings = input.servings,
        ingredients = input.ingredients,
        totalViews = input.totalViews,
        id = input.id,
        updatedAt = input.updatedAt
    )

    fun mapRecipeDomainToRecipeEntity(input: Recipe) = RecipeEntity(
        image = input.image,
        author = input.author,
        rating = input.rating,
        description = input.description,
        title = input.title,
        steps = input.steps,
        createdAt = input.createdAt,
        times = input.times,
        servings = input.servings,
        ingredients = input.ingredients,
        totalViews = input.totalViews,
        id = input.id,
        updatedAt = input.updatedAt
    )

    fun mapRecipeResponseToRecipeEntity(input: List<RecipeItem>): List<RecipeEntity> =
        input.map {
            RecipeEntity(
                image = it.image,
                author = it.author,
                rating = it.rating,
                description = it.description,
                title = it.title,
                steps = it.steps,
                createdAt = it.createdAt,
                times = it.times,
                servings = it.servings,
                ingredients = it.ingredients,
                totalViews = it.totalViews,
                id = it.id,
                updatedAt = it.updatedAt
            )
        }

    fun mapRecipeResponseToRecipeEntity(input: RecipeItem) = RecipeEntity(
        image = input.image,
        author = input.author,
        rating = input.rating,
        description = input.description,
        title = input.title,
        steps = input.steps,
        createdAt = input.createdAt,
        times = input.times,
        servings = input.servings,
        ingredients = input.ingredients,
        totalViews = input.totalViews,
        id = input.id,
        updatedAt = input.updatedAt
    )

    //    PROFILE data mapper
    fun mapProfileResponseToProfileEntity(input: ProfileItem) = ProfileEntity(
        bookmarks = input.bookmarks,
        lat = input.lat,
        lon = input.lon,
        id = input.id,
        email = input.email,
        picture = input.picture,
        username = input.username
    )

    fun mapProfileEntityToProfileDomain(input: ProfileEntity) = Profile(
        bookmarks = input.bookmarks,
        lat = input.lat,
        lon = input.lon,
        id = input.id,
        email = input.email,
        picture = input.picture,
        username = input.username
    )

    fun mapProfileDomainToProfileEntity(input: Profile) = ProfileEntity(
        bookmarks = input.bookmarks,
        lat = input.lat,
        lon = input.lon,
        id = input.id,
        email = input.email,
        picture = input.picture,
        username = input.username
    )
}