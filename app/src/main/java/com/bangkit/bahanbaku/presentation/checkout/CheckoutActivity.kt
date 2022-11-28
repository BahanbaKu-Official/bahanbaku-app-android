package com.bangkit.bahanbaku.presentation.checkout

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.adapter.CheckoutAdapter
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.domain.model.CheckoutDataClass
import com.bangkit.bahanbaku.core.utils.ERROR_DEFAULT_MESSAGE
import com.bangkit.bahanbaku.core.utils.addressObjectToString
import com.bangkit.bahanbaku.databinding.ActivityCheckoutBinding
import com.bangkit.bahanbaku.presentation.address.AddressActivity
import com.bangkit.bahanbaku.presentation.login.LoginActivity
import com.bangkit.bahanbaku.presentation.paymentmethod.PaymentMethodActivity
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class CheckoutActivity : AppCompatActivity() {

    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }

    private val viewModel: CheckoutViewModel by viewModels()

    private val recipe = MutableLiveData<CheckoutDataClass>()
    private val isAddressNotEmpty = MutableLiveData(false)
    private lateinit var recipeName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recipe.postValue(intent.getParcelableExtra(EXTRA_RECIPE)!!)
        recipeName = intent.getStringExtra(EXTRA_FOOD_NAME) ?: ""
        binding.tvTitleCheckoutRecipe.text = recipeName

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
                if (isAddressNotEmpty.value == true && recipe.value != null) {
                    val intent = Intent(this, PaymentMethodActivity::class.java)
                    intent.putExtra(PaymentMethodActivity.EXTRA_PRODUCTS, recipe.value)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, getString(R.string.pick_your_address), Toast.LENGTH_SHORT)
                        .show()
                }
            }

            binding.cvCheckoutAddress.layoutAddress.setOnClickListener {
                val intent = Intent(this, AddressActivity::class.java)
                intent.putExtra(EXTRA_RECIPE, recipe.value)
                intent.putExtra(
                    EXTRA_FOOD_NAME, recipeName
                )
                startActivity(intent)
            }
        }

        viewModel.getMainAddress().observe(this) {
            if (it.isNullOrEmpty()) {
                binding.cvCheckoutAddress.tvNoAddressMessage.visibility = View.VISIBLE
            } else {
                viewModel.getAddressById(token, it).observe(this) { result ->
                    when (result) {
                        is Resource.Loading -> {

                        }

                        is Resource.Error -> {
                            Toast.makeText(this, ERROR_DEFAULT_MESSAGE, Toast.LENGTH_SHORT).show()
                        }

                        is Resource.Success -> {
                            val data = result.data?.results
                            if (data != null) {
                                isAddressNotEmpty.postValue(true)
                                binding.cvCheckoutAddress.apply {
                                    tvAddressLabel.text = data.label
                                    tvNameUser.text = data.receiverName
                                    tvNoContact.text = data.receiverPhoneNumber

                                    tvAddress.text = addressObjectToString(result.data.results)
                                }
                            } else {
                                isAddressNotEmpty.postValue(false)
                                binding.cvCheckoutAddress.tvNoAddressMessage.visibility =
                                    View.VISIBLE
                            }
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