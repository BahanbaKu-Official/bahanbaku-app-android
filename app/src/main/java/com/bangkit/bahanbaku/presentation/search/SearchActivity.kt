package com.bangkit.bahanbaku.presentation.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.adapter.SearchRecipeAdapter
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.utils.ERROR_DEFAULT_MESSAGE
import com.bangkit.bahanbaku.databinding.ActivitySearchBinding
import com.bangkit.bahanbaku.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private val binding: ActivitySearchBinding by lazy {
        ActivitySearchBinding.inflate(layoutInflater)
    }

    private val viewModel: SearchViewModel by viewModels()
    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getToken()
    }

    private fun getToken() {
        viewModel.getToken().observe(this) {
            if (it.length <= 5) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            } else {
                val token = "Bearer $it"
                this.token = token

                val extraHighlight = intent.getStringExtra(EXTRA_HIGHLIGHT)
                if (!extraHighlight.isNullOrEmpty()) {
                    searchRecipe(token, extraHighlight)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchMenuItem = menu.findItem(R.id.search)
        val searchView = searchMenuItem.actionView as SearchView
        searchMenuItem.expandActionView()

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = getString(R.string.search_for_recipes)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    if (token.isNotEmpty()) {
                        searchRecipe(token, query)
                    }
                }
                searchMenuItem.expandActionView()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun searchRecipe(token: String, query: String) {
        viewModel.searchRecipe(token, query).observe(this@SearchActivity) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.shimmerSearchList.startShimmer()
                    binding.shimmerSearchList.isVisible = true
                    binding.imgNotFound.isVisible = false
                }
                is Resource.Error -> {
                    binding.shimmerSearchList.stopShimmer()
                    binding.shimmerSearchList.isVisible = false

                    val error = result.message
                    Toast.makeText(
                        this@SearchActivity,
                        error ?: ERROR_DEFAULT_MESSAGE,
                        Toast.LENGTH_SHORT
                    ).show()

                    binding.imgNotFound.isVisible = true
                    binding.rvRecipes.isVisible = false
                }
                is Resource.Success -> {
                    binding.shimmerSearchList.stopShimmer()
                    binding.shimmerSearchList.isVisible = false
                    binding.imgNotFound.isVisible = false
                    binding.rvRecipes.isVisible = true
                    val data = result.data!!

                    if (data.isEmpty()) {
                        binding.imgNotFound.isVisible = true
                    }

                    binding.rvRecipes.apply {
                        adapter = SearchRecipeAdapter(data)
                        layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_HIGHLIGHT = "extra_highlight"
    }
}