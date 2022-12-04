package com.bangkit.bahanbaku.presentation.directpayment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.domain.model.CheckoutDataClass
import com.bangkit.bahanbaku.core.domain.model.ProductJSONFormat
import com.bangkit.bahanbaku.core.domain.model.ProductsData
import com.bangkit.bahanbaku.core.utils.ERROR_DEFAULT_MESSAGE
import com.bangkit.bahanbaku.databinding.ActivityDirectPaymentBinding
import com.bangkit.bahanbaku.presentation.directpaymentproof.DirectPaymentProofActivity
import com.bangkit.bahanbaku.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat


@AndroidEntryPoint
class DirectPaymentActivity : AppCompatActivity() {

    private val binding: ActivityDirectPaymentBinding by lazy {
        ActivityDirectPaymentBinding.inflate(layoutInflater)
    }

    private val viewModel: DirectPaymentViewModel by viewModels()
    private val products = MutableLiveData<CheckoutDataClass>()
    private var productsJson: ProductsData? = null
    private lateinit var recipeId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        products.postValue(intent.getParcelableExtra(EXTRA_PRODUCT_LIST))
        recipeId = intent.getStringExtra(EXTRA_RECIPE_ID) ?: ""

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
        binding.btnImgPaymentMethod.setOnClickListener {
            val clipboard: ClipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(
                getString(R.string.label_account_number),
                binding.tvAccountNumber.text.toString()
            )
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, getString(R.string.account_number_copied), Toast.LENGTH_SHORT)
                .show()
        }

        products.observe(this) {
            val products = it.list
            var totalPrice = 0
            products.forEach { product ->
                totalPrice += product.price * product.quantity
            }

            val formatter = DecimalFormat("#,###")
            val formattedNumber = formatter.format(totalPrice)

            binding.tvPaymentAmount.text =
                getString(R.string.format_currency_rupiah).format(formattedNumber)
        }

        viewModel.getDirectPaymentInfo(token).observe(this) { result ->
            when (result) {
                is Resource.Error -> {
                    binding.layoutDirectPayment.visibility = View.INVISIBLE
                    binding.shimmerDirectPayment.startShimmer()
                    binding.shimmerDirectPayment.visibility = View.VISIBLE

                    Toast.makeText(this, ERROR_DEFAULT_MESSAGE, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    binding.layoutDirectPayment.visibility = View.INVISIBLE
                    binding.shimmerDirectPayment.startShimmer()
                    binding.shimmerDirectPayment.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.layoutDirectPayment.visibility = View.VISIBLE
                    binding.shimmerDirectPayment.stopShimmer()
                    binding.shimmerDirectPayment.visibility = View.GONE

                    val data = result.data?.results
                    if (data != null) {
                        binding.tvAccountNumber.text = data.bankAccount
                    }
                }
            }
        }

        binding.btnUploadPaymentProof.setOnClickListener {
            if (products.value != null) {
                if (products.value != null) {
                    val data = products.value!!.list

                    val productList = arrayListOf<ProductJSONFormat>()
                    data.forEach { product ->
                        productList.add(
                            ProductJSONFormat(
                                id = product.productId,
                                quantity = product.quantity
                            )
                        )
                    }

                    viewModel.getAddress().observe(this) { addressId ->
                        if (addressId != null) {
                            viewModel.createDirectPayment(
                                token,
                                ProductsData(productList, addressId),
                                recipeId
                            )
                                .observe(this) { result ->
                                    when (result) {
                                        is Resource.Loading -> {

                                        }

                                        is Resource.Error -> {

                                        }

                                        is Resource.Success -> {
                                            val paymentData = result.data?.results
                                            if (paymentData != null) {
                                                val intent =
                                                    Intent(
                                                        this,
                                                        DirectPaymentProofActivity::class.java
                                                    )

                                                intent.putExtra(
                                                    DirectPaymentProofActivity.EXTRA_PAYMENT_ID,
                                                    paymentData.directPayId
                                                )

                                                startActivity(intent)
                                            }
                                        }
                                    }
                                }
                        } else {
                            Toast.makeText(this, ERROR_DEFAULT_MESSAGE, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
        }
    }

    companion object {
        const val EXTRA_PRODUCT_LIST = "extra_product_list"
        const val EXTRA_RECIPE_ID = "extra_recipe_id"
    }
}