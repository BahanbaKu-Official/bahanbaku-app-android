package com.bangkit.bahanbaku.presentation.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.utils.ERROR_DEFAULT_MESSAGE
import com.bangkit.bahanbaku.databinding.ActivityRegisterBinding
import com.bangkit.bahanbaku.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupView()
    }

    private fun setupView() {
        binding.btnRegister.setOnClickListener {
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            viewModel.register(firstName, lastName, email, password).observe(this) { result ->
                when (result) {
                    is Resource.Error -> {
                        val error = result.message
                        Log.d(TAG, error ?: ERROR_DEFAULT_MESSAGE)
                        binding.progressBar.isVisible = false
                        Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is Resource.Success -> {
                        Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT).show()
                        binding.progressBar.isVisible = false

                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }

        binding.loginGoogle.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
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

    companion object {
        const val TAG = "RegisterActivity.Log"
    }
}