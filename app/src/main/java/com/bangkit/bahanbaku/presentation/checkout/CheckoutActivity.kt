package com.bangkit.bahanbaku.presentation.checkout

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.adapter.CheckoutAdapter
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.domain.model.CheckoutDataClass
import com.bangkit.bahanbaku.core.utils.addressObjectToString
import com.bangkit.bahanbaku.databinding.ActivityCheckoutBinding
import com.bangkit.bahanbaku.presentation.address.AddressActivity
import com.bangkit.bahanbaku.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class CheckoutActivity : AppCompatActivity() {

    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }

    private val viewModel: CheckoutViewModel by viewModels()

    private val recipe = MutableLiveData<CheckoutDataClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recipe.postValue(intent.getParcelableExtra(EXTRA_RECIPE)!!)
        binding.tvTitleCheckoutRecipe.text = intent.getStringExtra(EXTRA_FOOD_NAME)

        getToken()
    }

    private fun setupView(token: String) {
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

            val formatter = DecimalFormat("#,###")
            val price: Int = totalPrice
            val formattedNumber = formatter.format(price)

            binding.tvCheckoutTotalPrice.text =
                this.getString(R.string.format_currency_rupiah).format(formattedNumber)

            binding.btnPayCheckout.setOnClickListener {
                Toast.makeText(this, "This feature will be coming very soon!", Toast.LENGTH_SHORT)
                    .show()
            }

            binding.cvCheckoutAddress.layoutAddress.setOnClickListener {
                val intent = Intent(this, AddressActivity::class.java)
                startActivity(intent)
            }
        }

        viewModel.getAddress(token).observe(this) { result ->
            when (result) {
                is Resource.Loading -> {

                }

                is Resource.Error -> {

                }

                is Resource.Success -> {
                    val data = result.data?.results?.get(0)

                    if (data != null) {
                        binding.cvCheckoutAddress.apply {
                            tvAddressLabel.text = data.label
                            tvNameUser.text = ""

                            tvAddress.text = addressObjectToString(result.data.results[0])
                        }
                    }
                }

            }
        }
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
                setupView(token)
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