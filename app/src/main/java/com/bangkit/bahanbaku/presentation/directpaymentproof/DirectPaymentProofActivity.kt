package com.bangkit.bahanbaku.presentation.directpaymentproof

import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.utils.fromUriToFile
import com.bangkit.bahanbaku.databinding.ActivityDirectPaymentBinding
import com.bangkit.bahanbaku.databinding.ActivityDirectPaymentProofBinding
import com.bangkit.bahanbaku.presentation.login.LoginActivity
import com.bangkit.bahanbaku.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class DirectPaymentProofActivity : AppCompatActivity() {

    private val binding: ActivityDirectPaymentProofBinding by lazy {
        ActivityDirectPaymentProofBinding.inflate(layoutInflater)
    }

    private val viewModel: DirectPaymentProofViewModel by viewModels()
    private var token: String? = null
    private var file: File? = null
    private var paymentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        paymentId = intent.getStringExtra(EXTRA_PAYMENT_ID)

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
                token = "Bearer $it"
                setupView(token!!)
            }
        }
    }

    private fun setupView(token: String) {
        binding.cardImgProof.setOnClickListener {
            startGallery()
        }

        binding.btnUploadProof.setOnClickListener {
            if (file != null && paymentId != null) {
                viewModel.submitPaymentProof(token, file!!, paymentId!!).observe(this) { result ->
                    when (result) {
                        is Resource.Loading -> {

                        }

                        is Resource.Error -> {
                            Toast.makeText(this, result.data?.message.toString(), Toast.LENGTH_SHORT).show()
                        }

                        is Resource.Success -> {
                            Toast.makeText(
                                this,
                                getString(R.string.upload_success),
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = fromUriToFile(selectedImg, this@DirectPaymentProofActivity)
            binding.imgPaymentProof.setImageURI(selectedImg)
            binding.tvUploadPicture.visibility = View.GONE

            file = myFile
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.choose_payment_proof))
        launcherIntentGallery.launch(chooser)
    }

    companion object {
        const val EXTRA_PAYMENT_ID = "extra_payment_id"
    }
}