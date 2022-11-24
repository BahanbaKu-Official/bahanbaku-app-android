package com.bangkit.bahanbaku.presentation.address

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.bahanbaku.core.adapter.AddressListAdapter
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.databinding.ActivityAddressBinding
import com.bangkit.bahanbaku.presentation.addressmaps.AddressMapsActivity
import com.bangkit.bahanbaku.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressActivity : AppCompatActivity() {

    private val binding: ActivityAddressBinding by lazy {
        ActivityAddressBinding.inflate(layoutInflater)
    }

    private val viewModel: AddressViewModel by viewModels()

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
        binding.fabCookGuide.setOnClickListener {
            val intent = Intent(this, AddressMapsActivity::class.java)
            startActivity(intent)
        }

        viewModel.getAddress(token).observe(this) { result ->
            when (result) {
                is Resource.Loading -> {

                }

                is Resource.Error -> {

                }

                is Resource.Success -> {
                    if (!result.data?.results.isNullOrEmpty()) {
                        val data = result.data?.results
                        if (data != null) {
                            viewModel.getProfile(token).observe(this) { profileResult ->
                                when (profileResult) {
                                    is Resource.Loading -> {

                                    }

                                    is Resource.Error -> {

                                    }

                                    is Resource.Success -> {
                                        val profile = profileResult.data
                                        if (profile != null) {
                                            binding.rvAddress.apply {
                                                adapter = AddressListAdapter(data, profile)
                                                layoutManager =
                                                    LinearLayoutManager(this@AddressActivity)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}