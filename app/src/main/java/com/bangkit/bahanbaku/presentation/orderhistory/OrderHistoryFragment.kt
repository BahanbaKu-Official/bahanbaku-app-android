package com.bangkit.bahanbaku.presentation.orderhistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkit.bahanbaku.databinding.FragmentListOrderHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderHistoryFragment : Fragment() {

    private val binding: FragmentListOrderHistoryBinding by lazy {
        FragmentListOrderHistoryBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}