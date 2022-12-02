package com.bangkit.bahanbaku.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.databinding.ActivityLoginBinding
import com.bangkit.bahanbaku.presentation.main.MainActivity
import com.bangkit.bahanbaku.presentation.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        setupView()
    }

    private fun setupView() {

//        Toast.makeText(this, "For this testing, press LOGIN directly!", Toast.LENGTH_LONG).show()

        binding.btnLogin.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

//            viewModel.saveToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJEMUxzVDRFalN0NVE4STYyIiwidXNlcm5hbWUiOiJoZWl6b3UiLCJyb2xlIjoidXNlciIsImlhdCI6MTY2NTc2NzI5MX0.umyJT-AxJ38q-w7OE4FqBdGNGgvS0dP7e6zOKLqrV6c")
//            val intent = Intent(this@LoginActivity, MainActivity::class.java)
//            startActivity(intent)
//            finish()

            binding.loginEmail.doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrEmpty()) {
                    binding.textfieldLoginEmail.error = getString(R.string.cannot_empty)
                } else {
                    binding.textfieldLoginPassword.error = null
                }
            }

            binding.loginPassword.doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrEmpty()) {
                    binding.textfieldLoginPassword.error = getString(R.string.cannot_empty)
                } else {
                    binding.textfieldLoginPassword.error = null
                }
            }

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT)
                    .show()
            } else {
                viewModel.login(email, password).observe(this) { result ->
                    when (result) {
                        is Resource.Loading -> {
                            binding.progressBar.isVisible = true
                        }
                        is Resource.Error -> {
                            val message = result.message
                            if (!message.isNullOrEmpty()) {
                                val error = message.split(" ")

                                if ("401" in error) {
                                    binding.progressBar.isVisible = false
                                    Toast.makeText(
                                        this,
                                        getString(R.string.wrong_credentials),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        this,
                                        getString(R.string.login_failed),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    this,
                                    getString(R.string.login_failed),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        is Resource.Success -> {
                            val token = result.data!!.token
                            binding.progressBar.isVisible = false
                            Log.d(TAG, "Token: $token")
                            viewModel.saveToken(token)

                            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
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
        const val TAG = "LoginActivitylog"
    }
}