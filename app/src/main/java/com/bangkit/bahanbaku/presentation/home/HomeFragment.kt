package com.bangkit.bahanbaku.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bangkit.bahanbaku.core.adapter.RecipeCardLargeAdapter
import com.bangkit.bahanbaku.core.adapter.RecipeCardMediumAdapter
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.databinding.FragmentHomeBinding
import com.bangkit.bahanbaku.presentation.login.LoginActivity
import com.bangkit.bahanbaku.presentation.search.SearchActivity
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

        if (token != null) {
            val token = this.token as String

            viewModel.getRecipes(token).observe(requireActivity()) { result ->
                when (result) {
                    is Resource.Loading -> {

                    }

                    is Resource.Error -> {
                    }

                    is Resource.Success -> {
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
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}