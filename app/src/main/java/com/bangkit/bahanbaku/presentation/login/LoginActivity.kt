package com.bangkit.bahanbaku.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.bahanbaku.databinding.ActivityLoginBinding
import com.bangkit.bahanbaku.presentation.landing.LandingPageActivity
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

        setupView()
    }

    private fun setupView() {
        viewModel.isFirstTime().observe(this) {
            if (it) {
                val intent = Intent(this, LandingPageActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginName.text.toString()

            viewModel.saveToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJEMUxzVDRFalN0NVE4STYyIiwidXNlcm5hbWUiOiJoZWl6b3UiLCJyb2xlIjoidXNlciIsImlhdCI6MTY2NTc2NzI5MX0.umyJT-AxJ38q-w7OE4FqBdGNGgvS0dP7e6zOKLqrV6c")
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()

//            viewModel.login(email, password).observe(this) { result ->
//                when (result) {
//                    is Resource.Loading -> {
//                        binding.progressBar.isVisible = true
//                    }
//                    is Resource.Error -> {
//                        val message = result.message
//                        if (!message.isNullOrEmpty()) {
//                            val error = message.split(" ")
//
//                            if ("401" in error) {
//                                binding.progressBar.isVisible = false
//                                Toast.makeText(
//                                    this,
//                                    getString(R.string.wrong_credentials),
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            } else {
//                                Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show()
//                            }
//                        } else {
//                            Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                    is Resource.Success -> {
//                        val token = result.data!!.token
//                        binding.progressBar.isVisible = false
//                        Log.d(TAG, "Token: $token")
//                        viewModel.saveToken(token)
//
//                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
//                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                        startActivity(intent)
//                        finish()
//                    }
//                }
//            }
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