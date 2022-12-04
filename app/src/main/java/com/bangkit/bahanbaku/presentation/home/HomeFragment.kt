package com.bangkit.bahanbaku.presentation.home

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
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
import com.bangkit.bahanbaku.core.domain.model.Profile
import com.bangkit.bahanbaku.core.utils.categories
import com.bangkit.bahanbaku.databinding.FragmentHomeBinding
import com.bangkit.bahanbaku.presentation.login.LoginActivity
import com.bangkit.bahanbaku.presentation.profile.ProfileActivity
import com.bangkit.bahanbaku.presentation.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

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

        binding.btnIconCart.setOnClickListener {
            Toast.makeText(
                requireContext(),
                getString(R.string.coming_soon_string),
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.btnIconProfile.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)

//            Toast.makeText(
//                requireContext(),
//                "This feature will be available soon",
//                Toast.LENGTH_SHORT
//            ).show()
        }
    }

    private fun setupData(viewModel: HomeViewModel) {
        var categoriesSpanCount = 3

        when (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) {
            Configuration.SCREENLAYOUT_SIZE_LARGE -> categoriesSpanCount = 3
            Configuration.SCREENLAYOUT_SIZE_XLARGE -> categoriesSpanCount = 6
        }

        binding.rvRecipeCategories.apply {
            adapter = HomeCategoriesAdapter(categories)
            layoutManager = GridLayoutManager(requireContext(), categoriesSpanCount)
        }

        val calendar = Calendar.getInstance()
        val time = when (calendar.get(Calendar.HOUR_OF_DAY)) {
            in 2.until(11) -> getString(R.string.morning)
            in 11.until(14) -> getString(R.string.day)
            in 14.until(16) -> getString(R.string.afternoon)
            in 16.until(19) -> getString(R.string.evening)
            else -> getString(R.string.night)
        }

        loadGreetings(viewModel, time)
        loadData(viewModel)

    }

    private fun loadGreetings(viewModel: HomeViewModel, time: String) {
        if (token != null) {
            val token = this.token as String

            viewModel.getProfile(token).observe(requireActivity()) { result ->
                when (result) {
                    is Resource.Loading -> {
                        binding.tvHomeGreetings.isVisible = false
                    }

                    is Resource.Error -> {
                        binding.tvHomeGreetings.visibility = View.GONE
                    }

                    is Resource.Success -> {
                        val data = result.data as Profile
                        binding.tvHomeGreetings.isVisible = true
                        binding.tvHomeGreetings.text =
                            getString(R.string.format_greetings).format(time, data.firstName)


                    }
                }
            }
        }
    }

    private fun loadData(viewModel: HomeViewModel) {
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
                            32
                        )

                        constraintSet.connect(
                            R.id.tv_label_recommendation_2,
                            ConstraintSet.TOP,
                            R.id.rv_recipe_recommendation_1,
                            ConstraintSet.BOTTOM,
                            32
                        )

                        constraintSet.connect(
                            R.id.tv_label_others,
                            ConstraintSet.TOP,
                            R.id.rv_recipe_recommendation_2,
                            ConstraintSet.BOTTOM,
                            32
                        )

                        constraintSet.applyTo(constraintLayout)

                        binding.shimmerMorningRecommendationRecipe.isVisible = false
                        binding.shimmerRecipeRecommendation1.isVisible = false
                        binding.shimmerRecipeRecommendation2.isVisible = false

                        var spanCount = 2

                        when (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) {
                            Configuration.SCREENLAYOUT_SIZE_LARGE -> spanCount = 3
                            Configuration.SCREENLAYOUT_SIZE_XLARGE -> spanCount = 4
                        }

                        val data = result.data!!
                        binding.rvMoreRecipes.apply {
                            adapter = RecipeCardLargeAdapter(data)
                            layoutManager = StaggeredGridLayoutManager(
                                spanCount, LinearLayoutManager.VERTICAL
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