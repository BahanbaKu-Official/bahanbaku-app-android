package com.bangkit.bahanbaku.core.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.data.remote.response.RecipeItem
import com.bangkit.bahanbaku.databinding.ItemBookmarkBinding
import com.bangkit.bahanbaku.presentation.detail.DetailActivity
import com.bumptech.glide.Glide

class BookmarkAdapter :
    ListAdapter<RecipeItem, BookmarkAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)

        holder.binding.cardBookmark.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_RECIPE_ID, recipe.recipeId)
            holder.itemView.context.startActivity(intent)
        }
    }

    inner class ViewHolder(val binding: ItemBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: RecipeItem) {
            binding.tvRecipe.text = recipe.title
            binding.tvRecipeDescription.text = recipe.description
            binding.tvServings.text =
                itemView.context.getString(R.string.serving).format(recipe.portion)

            binding.rating.tvRating.text = recipe.rating.toString()

            Glide.with(itemView.context)
                .load(recipe.imageUrl)
                .into(binding.imgRecipe)
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<RecipeItem> = object : DiffUtil.ItemCallback<RecipeItem>() {
            override fun areItemsTheSame(oldItem: RecipeItem, newItem: RecipeItem): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: RecipeItem, newItem: RecipeItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}