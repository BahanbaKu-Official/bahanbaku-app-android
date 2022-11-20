package com.bangkit.bahanbaku.core.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.domain.model.Recipe
import com.bangkit.bahanbaku.databinding.ItemCardRecipeLargeBinding
import com.bangkit.bahanbaku.databinding.ItemRecipeSearchListBinding
import com.bangkit.bahanbaku.presentation.detail.DetailActivity
import com.bumptech.glide.Glide

class SearchRecipeAdapter(private val recipes: List<Recipe>) :
    RecyclerView.Adapter<SearchRecipeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCardRecipeLargeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)

        holder.binding.cardRecipe.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_RECIPE_ID, recipe.recipeId)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = recipes.size

    inner class ViewHolder(val binding: ItemCardRecipeLargeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            binding.tvRecipeName.text = recipe.title
            binding.tvRecipeDescription.text = recipe.description
            binding.tvTime.text =
                itemView.context.getString(R.string.format_time_minute).format(recipe.time)

            binding.tvRating.text = recipe.rating.toString()

            Glide.with(itemView)
                .load(recipe.imageUrl)
                .into(binding.imgRecipe)
        }
    }
}