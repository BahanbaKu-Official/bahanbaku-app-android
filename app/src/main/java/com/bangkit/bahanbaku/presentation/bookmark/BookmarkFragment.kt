package com.bangkit.bahanbaku.presentation.bookmark

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bangkit.bahanbaku.core.adapter.BookmarkAdapter
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.utils.ERROR_DEFAULT_MESSAGE
import com.bangkit.bahanbaku.databinding.FragmentBookmarkBinding
import com.bangkit.bahanbaku.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private val binding: FragmentBookmarkBinding by lazy {
        FragmentBookmarkBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: BookmarkAdapter
    private var token: String? = null
    val viewModel: BookmarkViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = BookmarkAdapter()
        binding.rvBookmark.apply {
            adapter = this@BookmarkFragment.adapter
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        }
    }

    override fun onResume() {
        super.onResume()
        getToken(viewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.root.removeAllViews()
    }

    private fun getToken(viewModel: BookmarkViewModel) {
        viewModel.getToken().observe(requireActivity()) {
            if (it == "null") {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } else {
                token = "Bearer $it"
                setupView(viewModel)
            }
        }
    }

    private fun setupView(viewModel: BookmarkViewModel) {
        if (token != null) {
//            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//                override fun onMove(
//                    recyclerView: RecyclerView,
//                    viewHolder: RecyclerView.ViewHolder,
//                    target: RecyclerView.ViewHolder
//                ): Boolean {
//                    return false
//                }
//
//                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                    viewModel.deleteBookmarks(token as String, viewHolder.adapterPosition)
//                        .observe(requireActivity()) { result ->
//                            if (result is Resource.Success) {
//                                updateList()
//                            }
//                        }
//                }
//            }).attachToRecyclerView(binding.rvBookmark)
//
            if (activity != null) {
                updateList()
            }
        }
    }

    private fun updateList() {
        viewModel.getBookmarks(token as String).observe(requireActivity()) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.shimmerFavoritesList.startShimmer()
                    binding.shimmerFavoritesList.showShimmer(true)
                    binding.shimmerFavoritesList.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    val error = result.message
                    Toast.makeText(requireContext(), error ?: ERROR_DEFAULT_MESSAGE, Toast.LENGTH_SHORT).show()
                    binding.shimmerFavoritesList.stopShimmer()
                    binding.shimmerFavoritesList.showShimmer(false)
                    binding.shimmerFavoritesList.visibility = View.GONE
                    binding.imgNoBookmark.isVisible = true
                }
                is Resource.Success -> {
                    binding.shimmerFavoritesList.stopShimmer()
                    binding.shimmerFavoritesList.showShimmer(false)
                    binding.shimmerFavoritesList.visibility = View.GONE
                    val data = result.data!!

                    adapter.submitList(data)
                    binding.imgNoBookmark.isVisible = data.isEmpty()
                }
            }
        }
    }
}