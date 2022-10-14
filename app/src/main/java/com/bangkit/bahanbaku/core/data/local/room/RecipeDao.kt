package com.bangkit.bahanbaku.core.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.bahanbaku.core.data.local.entity.RecipeEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getRecipes(): Flowable<List<RecipeEntity>>

    @Query("SELECT * FROM recipe WHERE recipeId = :recipeId")
    fun getRecipeById(recipeId: String): Flowable<RecipeEntity>

    @Query("DELETE FROM recipe")
    fun deleteAllRecipes()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipes(recipes: List<RecipeEntity>): Completable
}