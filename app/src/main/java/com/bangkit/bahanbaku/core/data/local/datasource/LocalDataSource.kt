package com.bangkit.bahanbaku.core.data.local.datasource

import com.bangkit.bahanbaku.core.data.local.entity.ProfileEntity
import com.bangkit.bahanbaku.core.data.local.entity.RecipeEntity
import com.bangkit.bahanbaku.core.data.local.room.ProfileDao
import com.bangkit.bahanbaku.core.data.local.room.RecipeDao
import io.reactivex.Flowable

class LocalDataSource private constructor(
    private val recipeDao: RecipeDao,
    private val profileDao: ProfileDao
) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(recipeDao: RecipeDao, profileDao: ProfileDao): LocalDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocalDataSource(recipeDao, profileDao)
            }
    }

    fun getAllRecipes(): Flowable<List<RecipeEntity>> = recipeDao.getRecipes()

    fun insertRecipes(recipeList: List<RecipeEntity>) = recipeDao.insertRecipes(recipeList)

    fun getRecipeById(id: String): Flowable<RecipeEntity> = recipeDao.getRecipeById(id)

    fun getProfile(): Flowable<List<ProfileEntity>> = profileDao.getProfile()

    fun insertProfile(profile: ProfileEntity) = profileDao.insertProfile(profile)

    fun deleteProfile() = profileDao.deleteAllProfile()
}