package com.bangkit.bahanbaku.presentation.orderdetail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.adapter.OrderDetailProductListAdapter
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.utils.addressObjectToString
import com.bangkit.bahanbaku.core.utils.currencyIntToString
import com.bangkit.bahanbaku.databinding.ActivityOrderDetailBinding
import com.bangkit.bahanbaku.presentation.detail.DetailActivity
import com.bangkit.bahanbaku.presentation.login.LoginActivity
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailActivity : AppCompatActivity() {

    private val binding: ActivityOrderDetailBinding by lazy {
        ActivityOrderDetailBinding.inflate(layoutInflater)
    }

    private val viewModel: OrderDetailViewModel by viewModels()

    private lateinit var orderId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        orderId = intent.getStringExtra(EXTRA_ORDER_ID) ?: ""

        getToken()
    }

    private fun setupView(token: String) {
        if (orderId.isNotEmpty()) {
            viewModel.getOrderDetail(token, orderId).observe(this) { result ->
                when (result) {
                    is Resource.Loading -> {

                    }

                    is Resource.Error -> {

                    }

                    is Resource.Success -> {
                        val data = result.data
                        if (data != null) {
                            binding.layoutCardRecipe.let {
                                it.tvRecipe.text = data.recipe.title
                                it.tvRecipeDescription.text = data.recipe.description
                                it.tvServings.text =
                                    getString(R.string.serving).format(data.recipe.portion)
                                it.tvRating.text = data.recipe.rating.toDouble().toString()
                                it.tvTime.text =
                                    getString(R.string.format_time_minute).format(data.recipe.time)

                                Glide.with(this).load(data.recipe.imageUrl).into(it.imgRecipe)

                                it.cardRecipe.setOnClickListener {
                                    val intent = Intent(this, DetailActivity::class.java)
                                    intent.putExtra(DetailActivity.EXTRA_RECIPE_ID, data.recipeId)
                                    startActivity(intent)
                                }
                            }

                            binding.tvOrderStatus.text = data.status.uppercase()
                            binding.rvOrderProducts.apply {
                                adapter = OrderDetailProductListAdapter(data.products)
                                layoutManager = LinearLayoutManager(this@OrderDetailActivity)
                            }

                            binding.tvOrderDate.text = data.createdAt

                            // TODO: SWITCH TO THE API ONE
                            var totalPrice = 0
                            data.products.forEach {
                                totalPrice += it.price
                            }

                            binding.tvTotalCost.text =
                                getString(R.string.format_currency_rupiah).format(
                                    currencyIntToString(totalPrice)
                                )

                            binding.tvAddress.text = addressObjectToString(data.address)
                            binding.tvReceiverName.text = data.address.receiverName
                            binding.tvReceiverNumber.text = data.address.receiverPhoneNumber

                            binding.btnOpenMaps.visibility = View.GONE
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

    companion object {
        const val EXTRA_ORDER_ID = "extra_order_id"
    }
}