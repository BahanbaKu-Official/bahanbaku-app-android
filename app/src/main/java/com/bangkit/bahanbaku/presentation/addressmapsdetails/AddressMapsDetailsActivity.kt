package com.bangkit.bahanbaku.presentation.addressmapsdetails

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.domain.model.AddressInput
import com.bangkit.bahanbaku.databinding.ActivityAddressMapsDetailsBinding
import com.bangkit.bahanbaku.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressMapsDetailsActivity : AppCompatActivity() {

    private val binding: ActivityAddressMapsDetailsBinding by lazy {
        ActivityAddressMapsDetailsBinding.inflate(layoutInflater)
    }

    private val viewModel: AddressMapsDetailsViewModel by viewModels()

    private var address: AddressInput? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        address = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_ADDRESS, AddressInput::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_ADDRESS)
        }

        getToken(address)
    }

    private fun setupView(token: String, address: AddressInput?) {
        if (address != null) {
            binding.tfLabel.editText?.setText(address.label)
            binding.tfStreet.editText?.setText(address.street)
            binding.tfDistrict.editText?.setText(address.district)
            binding.tfCity.editText?.setText(address.city)
            binding.tfProvince.editText?.setText(address.province)
            binding.tfZipCode.editText?.setText(address.zipCode.toString())
        }

        binding.btnSaveAddress.setOnClickListener {
            val label = binding.etLabel.text.toString()

            if (label.isEmpty()) {
                Toast.makeText(this, "Label cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                address?.apply {
                    viewModel.addAddress(
                        token, street, district, city, province, zipCode, label
                    ).observe(this@AddressMapsDetailsActivity) { result ->
                        when (result) {
                            is Resource.Loading -> {

                            }

                            is Resource.Error -> {

                            }

                            is Resource.Success -> {
                                Toast.makeText(
                                    this@AddressMapsDetailsActivity,
                                    "Success",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getToken(address: AddressInput?) {
        viewModel.getToken().observe(this) {
            if (it == "null") {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            } else {
                val token = "Bearer $it"
                setupView(token, address)
            }
        }
    }

    companion object {
        const val EXTRA_ADDRESS = "extra_address"
    }
}