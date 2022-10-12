package com.bangkit.bahanbaku.core.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bahanbaku.core.domain.model.Recipe
import com.bangkit.bahanbaku.databinding.ItemCardRecipeMediumTimeBinding
import com.bangkit.bahanbaku.presentation.detail.DetailActivity
import com.bumptech.glide.Glide

class RecipeCardMediumTimeAdapter(private val list: List<Recipe>) : RecyclerView.Adapter<RecipeCardMediumTimeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardRecipeMediumTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = list[position]
        holder.bind(recipe)

        holder.binding.cardRecipe.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_RECIPE_ID, recipe.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(val binding: ItemCardRecipeMediumTimeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe?) {
            binding.let {
                binding.tvRecipeName.text = recipe?.title
                binding.tvRating.text = recipe?.rating.toString()

                // TODO: Implement time

                Glide.with(itemView.context)
                    .load(recipe?.image)
                    .into(binding.imgRecipe)
            }
        }
    }
}