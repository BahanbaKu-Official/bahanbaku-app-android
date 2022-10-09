package com.bangkit.bahanbaku.presentation.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.bahanbaku.core.adapter.DialogIngredientsAdapter
import com.bangkit.bahanbaku.core.data.local.entity.IngredientEntity
import com.bangkit.bahanbaku.core.domain.model.Recipe
import com.bangkit.bahanbaku.core.utils.setWidthPercent
import com.bangkit.bahanbaku.databinding.DialogIngredientsBinding
import com.bangkit.bahanbaku.presentation.ingredient.IngredientActivity
import com.bangkit.bahanbaku.presentation.updatelocation.UpdateLocationActivity

class IngredientsDialog : DialogFragment() {

    private val binding: DialogIngredientsBinding by lazy {
        DialogIngredientsBinding.inflate(layoutInflater)
    }

    private lateinit var detailActivity: DetailActivity
    private var recipe: Recipe? = null
    private val list = arrayListOf<IngredientEntity>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailActivity = requireActivity() as DetailActivity
        recipe = detailActivity.recipe
        setWidthPercent(90)

        setupView()
    }

    private fun setupView() {
        if (recipe != null) {
            detailActivity.cleanseIngredients(recipe!!.ingredients).forEach {
                list.add(IngredientEntity(it, true))
            }
        }

        binding.rvIngredient.apply {
            adapter = DialogIngredientsAdapter(list)
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.btnGetIngredients.setOnClickListener {
            val ingredientsCheckedList = arrayListOf<String>()
            list.forEach {
                if (it.checked) {
                    ingredientsCheckedList.add(it.ingredient)
                }
            }

            if (detailActivity.isLocationNull) {
                val intent = Intent(requireContext(), UpdateLocationActivity::class.java)
                intent.putExtra(UpdateLocationActivity.EXTRA_TO_INGREDIENTS, true)
                intent.putExtra(IngredientActivity.EXTRA_SEARCH, ingredientsCheckedList)
                startActivity(intent)
            } else {
                val intent = Intent(requireContext(), IngredientActivity::class.java)
                intent.putExtra(IngredientActivity.EXTRA_SEARCH, ingredientsCheckedList)
                startActivity(intent)
            }
        }
    }
}