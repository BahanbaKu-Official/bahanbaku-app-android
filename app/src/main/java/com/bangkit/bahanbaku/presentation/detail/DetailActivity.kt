package com.bangkit.bahanbaku.presentation.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.adapter.RecipeDetailIngredientsGridAdapter
import com.bangkit.bahanbaku.core.adapter.RecipeDetailStepsListAdapter
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.data.remote.response.RecipeDetailItem
import com.bangkit.bahanbaku.core.utils.ERROR_DEFAULT_MESSAGE
import com.bangkit.bahanbaku.databinding.ActivityDetailBinding
import com.bangkit.bahanbaku.presentation.login.LoginActivity
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailViewModel by viewModels()
    private var isRecipeBookmarked = MutableLiveData(false)
    internal var recipe: RecipeDetailItem? = null
    internal var isLocationNull = true
    private var token: String? = null
    private var isBookmarkChanged = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBarRecipeDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


//        getToken()
    }

    private fun getToken() {
        viewModel.getToken().observe(this) {
            if (it == "null") {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            } else {
                token = "Bearer $it"
                setupView(token!!)
            }
        }
    }

    private fun setupView(token: String) {
        viewModel.getProfile(token).observe(this) {
            if (it is Resource.Success) {
                val lat = it.data!!.lat
                val lon = it.data.lon
                isLocationNull = (lat == 0.0 && lon == 0.0)
            }
        }

        val recipeId = intent.getStringExtra(EXTRA_RECIPE_ID)
        if (recipeId != null) {
            viewModel.getRecipe(token, recipeId).observe(this) { result ->
                when (result) {
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is Resource.Error -> {
                        binding.progressBar.isVisible = false
                        val error = result.message
                        Log.d(TAG, error ?: ERROR_DEFAULT_MESSAGE)
                    }
                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        val recipe = result.data!!
                        this.recipe = recipe
                        checkIfRecipeBookmarked(token, recipe.recipeId)

                        supportActionBar?.title = recipe.title
                        binding.tvDescription.text = recipe.description
                        binding.tvServings.text = "${recipe.portion} servings"

                        binding.rvIngredients.apply {
                            adapter = RecipeDetailIngredientsGridAdapter(recipe.ingredients)
                            layoutManager = GridLayoutManager(this@DetailActivity, 2)
                        }

                        binding.rvInstructions.apply {
                            adapter = RecipeDetailStepsListAdapter(recipe.steps)
                            layoutManager = LinearLayoutManager(this@DetailActivity)
                        }

                        Glide.with(this)
                            .load(recipe.imageUrl)
                            .into(binding.imgRecipe)
                    }
                }
            }
        }

//        binding.btnCheckIngredients.setOnClickListener {
//            val ingredients = recipe?.ingredients
//            if (ingredients != null) {
//                ingredientsDialog = IngredientsDialog()
//                ingredientsDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog)
//                ingredientsDialog.show(supportFragmentManager, INGREDIENTS_DIALOG)
//            }
//        }
    }

    internal fun cleanseIngredients(list: List<String>): ArrayList<String> {
        val arrayList = arrayListOf<String>()
        list.forEach { s ->
            arrayList.add(s)
        }

        val outputList = arrayListOf<String>()


        for (data in arrayList) {
            val re = Regex("^[0-9].*")
            var ingredient = data.split(',')[0]
            if (re.matches(ingredient)) {
                val arrayIngredient = ingredient.split(' ')
                ingredient = ""
                for (i in 2 until arrayIngredient.size) {
                    ingredient += arrayIngredient[i] + " "
                }
                ingredient = ingredient.split('(')[0]
            }
            ingredient = ingredient.trim()
            outputList.add(ingredient)
        }

        return outputList
    }

    private fun checkIfRecipeBookmarked(token: String, id: String) {
//        viewModel.checkIfRecipeBookmarked(token, id).observe(this) {
//            isRecipeBookmarked.postValue(it)
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item_recipe, menu)
        isRecipeBookmarked.observe(this) { bookmarked ->
            if (bookmarked) {
                menu.findItem(R.id.bookmark).icon = AppCompatResources.getDrawable(
                    this,
                    R.drawable.ic_baseline_bookmark_24
                )
            } else {
                menu.findItem(R.id.bookmark).icon = AppCompatResources.getDrawable(
                    this,
                    R.drawable.ic_baseline_bookmark_border_24
                )
            }
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bookmark -> {
                isBookmarkChanged = true
                isRecipeBookmarked.postValue(!(isRecipeBookmarked.value as Boolean))
            }

            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
//        isRecipeBookmarked.observe(this) { bookmarked ->
//            if (isBookmarkChanged) {
//                if (bookmarked) {
//                    addBookmark()
//                } else {
//                    deleteBookmark()
//                }
//
//                isBookmarkChanged = false
//            }
//        }
    }

//    private fun deleteBookmark() {
//        viewModel.deleteBookmark(token as String, (recipe as Recipe).id)
//            .observe(this) { result ->
//                when (result) {
//                    is Resource.Loading -> {
//                        binding.progressBar.isVisible = true
//                    }
//                    is Resource.Error -> {
//                        binding.progressBar.isVisible = false
////                        val error = result.error
////                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
//                    }
//                    is Resource.Success -> {
//                        binding.progressBar.isVisible = false
//                        isRecipeBookmarked.postValue(false)
//                    }
//                }
//            }
//    }
//
//    private fun addBookmark() {
//        viewModel.addBookmark(token as String, (recipe as Recipe).id)
//            .observe(this) { result ->
//                when (result) {
//                    is Resource.Loading -> {
//                        binding.progressBar.isVisible = true
//                    }
//                    is Resource.Error -> {
//                        binding.progressBar.isVisible = false
////                        val error = result.error
////                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
//                    }
//                    is Resource.Success -> {
//                        binding.progressBar.isVisible = false
//                        isRecipeBookmarked.postValue(true)
//                    }
//                }
//            }
//    }

    companion object {
        private const val TAG = "DetailActivity.log"
        const val EXTRA_RECIPE_ID = "extra_recipe_id"
        const val INGREDIENTS_DIALOG = "ingredients_dialog"
    }
}