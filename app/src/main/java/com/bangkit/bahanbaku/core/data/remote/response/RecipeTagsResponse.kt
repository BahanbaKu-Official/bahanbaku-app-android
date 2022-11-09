package com.bangkit.bahanbaku.core.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class RecipeTagsResponse(

	@field:SerializedName("tags")
	val tags: List<TagsItem>
) : Parcelable

@Parcelize
data class Tags(

	@field:SerializedName("tagId")
	val tagId: String,

	@field:SerializedName("recipeId")
	val recipeId: String
) : Parcelable

@Parcelize
data class TagsItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("tagId")
	val tagId: String,

	@field:SerializedName("tag")
	val tag: String,

	@field:SerializedName("recipe_tags")
	val recipeTags: Tags,

	@field:SerializedName("updatedAt")
	val updatedAt: String
) : Parcelable
