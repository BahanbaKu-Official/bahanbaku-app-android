package com.bangkit.bahanbaku.presentation.checkout

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.bahanbaku.core.adapter.CheckoutAdapter
import com.bangkit.bahanbaku.core.data.remote.response.IngredientsItem
import com.bangkit.bahanbaku.core.domain.model.CheckoutDataClass
import com.bangkit.bahanbaku.databinding.ActivityCheckoutBinding

class CheckoutActivity : AppCompatActivity() {

    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }

    lateinit var recipe: CheckoutDataClass
    private val ingredientsList = ArrayList<IngredientsItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recipe = intent.getParcelableExtra(EXTRA_RECIPE)!!

        setupView()
    }

    private fun setupView() {
        binding.rvCheckout.apply {
            adapter = CheckoutAdapter(recipe.list)
            layoutManager = LinearLayoutManager(this@CheckoutActivity)
        }

        binding.btnPayCheckout.setOnClickListener {
            Toast.makeText(this, "This feature will be coming very soon!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    companion object {
        const val EXTRA_RECIPE = "extra_recipe"
    }
}