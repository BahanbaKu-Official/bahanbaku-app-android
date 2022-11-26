package com.bangkit.bahanbaku.presentation.forgotpassword

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.bahanbaku.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
    }
}