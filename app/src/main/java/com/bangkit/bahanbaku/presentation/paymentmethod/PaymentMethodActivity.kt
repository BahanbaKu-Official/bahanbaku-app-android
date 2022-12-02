package com.bangkit.bahanbaku.presentation.paymentmethod

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.adapter.PaymentMethodListAdapter
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.domain.model.CheckoutDataClass
import com.bangkit.bahanbaku.databinding.ActivityPaymentMethodBinding
import com.bangkit.bahanbaku.presentation.directpayment.DirectPaymentActivity
import com.bangkit.bahanbaku.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentMethodActivity : AppCompatActivity() {

    private val viewModel: PaymentMethodViewModel by viewModels()

    private val binding: ActivityPaymentMethodBinding by lazy {
        ActivityPaymentMethodBinding.inflate(layoutInflater)
    }

    private val products = MutableLiveData<CheckoutDataClass>()
    private lateinit var recipeId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        products.postValue(intent.getParcelableExtra(EXTRA_PRODUCTS))
        recipeId = intent.getStringExtra(DirectPaymentActivity.EXTRA_RECIPE_ID) ?: ""

        getToken()
    }

    private fun getToken() {
        viewModel.getToken().observe(this) {
            if (it == "null") {
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

    private fun setupView(token: String) {
        viewModel.getPaymentMethods(token).observe(this) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.layoutPaymentMethods.visibility = View.INVISIBLE
                    binding.shimmerPaymentMethod.startShimmer()
                    binding.shimmerPaymentMethod.visibility = View.VISIBLE
                }

                is Resource.Error -> {
                    binding.layoutPaymentMethods.visibility = View.INVISIBLE
                    binding.shimmerPaymentMethod.startShimmer()
                    binding.shimmerPaymentMethod.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.layoutPaymentMethods.visibility = View.VISIBLE
                    binding.shimmerPaymentMethod.stopShimmer()
                    binding.shimmerPaymentMethod.visibility = View.GONE

                    val data = result.data

                    if (data != null) {
                        binding.layoutPaymentMethodDirect.imgIconPaymentMethod.setImageDrawable(
                            ContextCompat.getDrawable(this, R.drawable.logo_jago)
                        )
                        binding.layoutPaymentMethodDirect.tvChoosePayment.text =
                            getString(R.string.bank_jago)
                        binding.tvLabelPaymentMethodDirect.text = getString(R.string.direct_payment)

                        binding.layoutPaymentMethodDirect.itemChildPayment.setOnClickListener {
                            if (products.value != null) {
                                val intent = Intent(this, DirectPaymentActivity::class.java)
                                intent.putExtra(
                                    DirectPaymentActivity.EXTRA_PRODUCT_LIST,
                                    products.value
                                )
                                intent.putExtra(
                                    DirectPaymentActivity.EXTRA_RECIPE_ID,
                                    recipeId
                                )
                                startActivity(intent)
                            }
                        }

                        binding.rvChildExpendablePaymentMethod.apply {
                            adapter = PaymentMethodListAdapter(data)
                            layoutManager = LinearLayoutManager(this@PaymentMethodActivity)
                        }
                    }

                }
            }
        }
    }

    companion object {
        const val EXTRA_PRODUCTS = "extra_products"
    }
}