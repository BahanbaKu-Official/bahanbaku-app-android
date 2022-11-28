package com.bangkit.bahanbaku.presentation.paymentmethod

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.adapter.PaymentMethodListAdapter
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.databinding.ActivityPaymentMethodBinding
import com.bangkit.bahanbaku.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentMethodActivity : AppCompatActivity() {

    private val viewModel: PaymentMethodViewModel by viewModels()

    private val binding: ActivityPaymentMethodBinding by lazy {
        ActivityPaymentMethodBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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

                }

                is Resource.Error -> {

                }

                is Resource.Success -> {
                    val data = result.data

                    if (data != null) {
                        binding.rvChildExpendablePaymentMethod.apply {
                            adapter = PaymentMethodListAdapter(data)
                            layoutManager = LinearLayoutManager(this@PaymentMethodActivity)
                        }
                    }

                }
            }
        }
    }
}