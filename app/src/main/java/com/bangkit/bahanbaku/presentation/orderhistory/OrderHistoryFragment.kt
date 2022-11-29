package com.bangkit.bahanbaku.presentation.orderhistory

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.bahanbaku.core.adapter.OrderHistoryAdapter
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.utils.ERROR_DEFAULT_MESSAGE
import com.bangkit.bahanbaku.databinding.FragmentListOrderHistoryBinding
import com.bangkit.bahanbaku.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderHistoryFragment : Fragment() {

    private val binding: FragmentListOrderHistoryBinding by lazy {
        FragmentListOrderHistoryBinding.inflate(layoutInflater)
    }

    private val viewModel: OrderHistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getToken()
    }

    private fun getToken() {
        viewModel.getToken().observe(requireActivity()) {
            Log.d("MAINACTV", "checking token")
            if (it == "null") {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } else {
                val token = "Bearer $it"
                setupView(token)
            }
        }
    }

    private fun setupView(token: String) {
        viewModel.getDirectOrderHistory(token).observe(requireActivity()) { result ->
            when (result) {
                is Resource.Loading -> {

                }

                is Resource.Error -> {
                    Toast.makeText(requireContext(), ERROR_DEFAULT_MESSAGE, Toast.LENGTH_SHORT)
                        .show()
                }

                is Resource.Success -> {
                    val data = result.data
                    if (!data.isNullOrEmpty()) {
                        binding.rvOrderHistory.apply {
                            adapter = OrderHistoryAdapter(data)
                            layoutManager = LinearLayoutManager(requireContext())
                        }
                    }
                }
            }
        }
    }
}