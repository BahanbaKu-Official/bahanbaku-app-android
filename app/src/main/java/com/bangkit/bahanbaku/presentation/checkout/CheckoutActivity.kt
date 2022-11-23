package com.bangkit.bahanbaku.presentation.checkout

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.adapter.CheckoutAdapter
import com.bangkit.bahanbaku.core.domain.model.CheckoutDataClass
import com.bangkit.bahanbaku.databinding.ActivityCheckoutBinding

class CheckoutActivity : AppCompatActivity() {

    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }

    private val recipe = MutableLiveData<CheckoutDataClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recipe.postValue(intent.getParcelableExtra(EXTRA_RECIPE)!!)
        binding.tvTitleCheckoutRecipe.text = intent.getStringExtra(EXTRA_FOOD_NAME)

        setupView()
    }

    private fun setupView() {
        recipe.observe(this) { data ->
            binding.rvCheckout.apply {
                adapter = CheckoutAdapter(data.list) {
                    recipe.postValue(data)
                }
                layoutManager = LinearLayoutManager(this@CheckoutActivity)
            }

            var totalPrice = 0

            data.list.forEach { product ->
                totalPrice += (product.price * product.quantity)
            }

            binding.tvCheckoutTotalPrice.text =
                this.getString(R.string.format_currency_rupiah).format(totalPrice)

            binding.btnPayCheckout.setOnClickListener {
                Toast.makeText(this, "This feature will be coming very soon!", Toast.LENGTH_SHORT)
                    .show()
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
        const val EXTRA_RECIPE = "extra_recipe"
        const val EXTRA_FOOD_NAME = "extra_food_name"
    }
}