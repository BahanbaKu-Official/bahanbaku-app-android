package com.bangkit.bahanbaku.core.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bahanbaku.core.domain.model.Recipe
import com.bangkit.bahanbaku.databinding.ItemCardRecipeMediumBinding
import com.bangkit.bahanbaku.presentation.detail.DetailActivity
import com.bumptech.glide.Glide

class RecipeCardMediumAdapter(
    private val list: List<Recipe>,
    private val isResourceImage: Boolean = false
) : RecyclerView.Adapter<RecipeCardMediumAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCardRecipeMediumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = list[position]
        holder.bind(recipe)

        holder.binding.cardRecipe.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_RECIPE_ID, recipe.recipeId)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(val binding: ItemCardRecipeMediumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe?) {
            binding.let {
                binding.tvRecipeName.text = recipe?.title
                binding.tvRecipeDescription.text = recipe?.description
                binding.tvRating.text = recipe?.rating.toString()

                Glide.with(itemView.context)
                    .load(if (isResourceImage) recipe?.imageResource else recipe?.imageUrl)
                    .into(binding.imgRecipe)
            }
        }
    }
}