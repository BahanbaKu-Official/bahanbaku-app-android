package com.bangkit.bahanbaku.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.adapter.HomeCategoriesAdapter
import com.bangkit.bahanbaku.core.adapter.RecipeCardLargeAdapter
import com.bangkit.bahanbaku.core.adapter.RecipeCardMediumAdapter
import com.bangkit.bahanbaku.core.adapter.RecipeCardMediumTimeAdapter
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.utils.categories
import com.bangkit.bahanbaku.core.utils.imagePlaceholderUrl
import com.bangkit.bahanbaku.databinding.FragmentHomeBinding
import com.bangkit.bahanbaku.presentation.login.LoginActivity
import com.bangkit.bahanbaku.presentation.search.SearchActivity
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val viewModel: HomeViewModel by viewModels()

    private var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("MAINACTV", "activity called")
        getToken(viewModel)
    }

    override fun onResume() {
        super.onResume()
        getToken(viewModel)
    }

    private fun getToken(viewModel: HomeViewModel) {
        viewModel.getToken().observe(requireActivity()) {
            Log.d("MAINACTV", "checking token")
            if (it == "null") {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } else {
                token = "Bearer $it"
                setupView()
                setupData(viewModel)
            }
        }
    }

    private fun setupView() {
        binding.cardSearch.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupData(viewModel: HomeViewModel) {
        Glide.with(requireContext())
            .load(imagePlaceholderUrl)
            .into(binding.imgBanner)

        binding.rvRecipeCategories.apply {
            adapter = HomeCategoriesAdapter(categories)
            layoutManager = GridLayoutManager(requireContext(), 3)
        }

        if (token != null) {
            val token = this.token as String

            viewModel.getRecipes(token).observe(requireActivity()) { result ->
                when (result) {
                    is Resource.Loading -> {
                        binding.shimmerMorningRecommendationRecipe.startShimmer()
                        binding.shimmerRecipeRecommendation1.startShimmer()
                        binding.shimmerRecipeRecommendation2.startShimmer()
                    }

                    is Resource.Error -> {

                    }

                    is Resource.Success -> {
                        val constraintLayout = binding.layoutConstraintHome
                        val constraintSet = ConstraintSet()
                        constraintSet.clone(constraintLayout)
                        constraintSet.connect(
                            R.id.tv_label_categories,
                            ConstraintSet.TOP,
                            R.id.rv_morning_recommendation_recipe,
                            ConstraintSet.BOTTOM,
                            16
                        )

                        constraintSet.connect(
                            R.id.tv_label_recommendation_2,
                            ConstraintSet.TOP,
                            R.id.rv_recipe_recommendation_1,
                            ConstraintSet.BOTTOM,
                            16
                        )

                        constraintSet.connect(
                            R.id.tv_label_others,
                            ConstraintSet.TOP,
                            R.id.rv_recipe_recommendation_2,
                            ConstraintSet.BOTTOM,
                            16
                        )

                        constraintSet.applyTo(constraintLayout)

                        binding.shimmerMorningRecommendationRecipe.isVisible = false
                        binding.shimmerRecipeRecommendation1.isVisible = false
                        binding.shimmerRecipeRecommendation2.isVisible = false

                        val data = result.data!!
                        binding.rvMoreRecipes.apply {
                            adapter = RecipeCardLargeAdapter(data)
                            layoutManager = StaggeredGridLayoutManager(
                                2, LinearLayoutManager.VERTICAL
                            )
                        }

                        binding.rvRecipeRecommendation1.apply {
                            adapter = RecipeCardMediumAdapter(data)
                            layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        }

                        binding.rvRecipeRecommendation2.apply {
                            adapter = RecipeCardMediumAdapter(data)
                            layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        }

                        binding.rvMorningRecommendationRecipe.apply {
                            adapter = RecipeCardMediumTimeAdapter(data)
                            layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}