package com.bangkit.bahanbaku.presentation.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.domain.model.Profile
import com.bangkit.bahanbaku.core.utils.AppExecutors
import com.bangkit.bahanbaku.core.utils.fromUriToFile
import com.bangkit.bahanbaku.core.utils.reduceFileImage
import com.bangkit.bahanbaku.databinding.ActivityProfileBinding
import com.bangkit.bahanbaku.presentation.login.LoginActivity
import com.bangkit.bahanbaku.presentation.preference.PreferenceFragment
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import kotlin.random.Random

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    private val binding: ActivityProfileBinding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    private val appExecutor: AppExecutors by lazy {
        AppExecutors()
    }

    private var compressingDone: MutableLiveData<Boolean> = MutableLiveData(false)

    private val viewModel: ProfileViewModel by viewModels()
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction().add(R.id.layout_preferences, PreferenceFragment())
            .commit()

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
                this.token = token
                setupView()
            }
        }
    }

    private fun setupView() {
        binding.imgProfile.setOnClickListener {
            startGallery()
        }

        binding.btnLogout.setOnClickListener {
            viewModel.deleteToken()
        }

        loadProfile()
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.import_picture))
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK) {
            val selectedImage: Uri = activityResult.data?.data as Uri
            var myFile = fromUriToFile(selectedImage, this)

            binding.progressBar.isVisible = true

            appExecutor.diskIO().execute {
                myFile = reduceFileImage(myFile)
                compressingDone.postValue(true)
            }

            compressingDone.observe(this) { done ->
                if (done) {
                    uploadPicture(myFile)
                }
            }
        }
    }

    private fun uploadPicture(myFile: File) {
        token?.let {
            viewModel.uploadPicture(it, myFile).observe(this) { result ->
                when (result) {
                    is Resource.Error -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(
                            this,
                            getString(R.string.profile_picture_update_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(
                            this,
                            getString(R.string.profile_picture_updated),
                            Toast.LENGTH_SHORT
                        ).show()

                        loadProfile()
                    }
                }
            }
        }
    }

    private fun loadProfile() {
        if (token != null) {
            viewModel.getProfile(token as String).observe(this) { result ->
                when (result) {
                    is Resource.Error -> {
                        binding.layoutPreferences.isVisible = true
                        binding.btnLogout.isVisible = true

                        binding.shimmerProfile.visibility = View.GONE
                        binding.shimmerProfile.stopShimmer()
                        binding.shimmerProfile.showShimmer(false)
                        Toast.makeText(
                            this,
                            getString(R.string.error_loading_profile),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is Resource.Loading -> {
                        binding.shimmerProfile.visibility = View.VISIBLE
                        binding.shimmerProfile.startShimmer()
                        binding.shimmerProfile.showShimmer(true)
                    }
                    is Resource.Success -> {
                        binding.layoutPreferences.isVisible = true
                        binding.btnLogout.isVisible = true

                        binding.shimmerProfile.visibility = View.GONE
                        binding.shimmerProfile.stopShimmer()
                        binding.shimmerProfile.showShimmer(false)
                        val data = result.data as Profile

                        binding.tvNameProfile.text =
                            getString(R.string.format_name).format(data.firstName, data.lastName)

                        Glide.with(this)
                            .load(data.profileImage)
                            .into(binding.imgProfile)

//                        Glide.with(this)
//                            .load(data.profileImage + "?rand=${Random(2000000)}")
//                            .apply(
//                                RequestOptions().signature(
//                                    ObjectKey(
//                                        System.currentTimeMillis().toString()
//                                    )
//                                )
//                            )
//                            .into(binding.imgProfile)

                        Glide.with(this)
                            .load(data.profileImage + "?rand=${Random(2000000)}")
                            .into(binding.imgProfile)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getToken()
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
}