package com.bangkit.bahanbaku.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.databinding.ActivityMainBinding
import com.bangkit.bahanbaku.presentation.camera.CameraActivity
import com.bangkit.bahanbaku.presentation.login.LoginActivity
import com.bangkit.bahanbaku.presentation.profile.ProfileActivity
import com.bangkit.bahanbaku.presentation.snapfood.SnapFoodActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()
        validateToken()

        val navView: BottomNavigationView = binding.navView
        navView.background = null
        navView.menu.getItem(2).isEnabled = false

        binding.fabMainCamera.setOnClickListener {
            val intent = Intent(this, SnapFoodActivity::class.java)
            startActivity(intent)
        }

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_bookmark,
                R.id.navigation_orders,
                R.id.navigation_notification
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun validateToken() {
        viewModel.getToken().observe(this) {
            if (it.length < 5) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }
    }
}