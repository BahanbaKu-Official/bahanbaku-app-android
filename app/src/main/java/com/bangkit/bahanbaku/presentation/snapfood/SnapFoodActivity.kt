package com.bangkit.bahanbaku.presentation.snapfood

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.utils.fromUriToFile
import com.bangkit.bahanbaku.databinding.ActivitySnapFoodBinding
import com.bangkit.bahanbaku.presentation.camera.CameraActivity
import com.bangkit.bahanbaku.presentation.snapfood.result.SnapFoodResultActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SnapFoodActivity : AppCompatActivity() {

    private val binding: ActivitySnapFoodBinding by lazy {
        ActivitySnapFoodBinding.inflate(layoutInflater)
    }

    private val viewModel: SnapFoodViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView(viewModel)
    }

    private fun setupView(viewModel: SnapFoodViewModel) {
        binding.btnTakePhoto.setOnClickListener {
            Toast.makeText(this, getString(R.string.opening_camera), Toast.LENGTH_SHORT).show()
            if (!allPermissionsGranted()) {
                ActivityCompat.requestPermissions(
                    this,
                    REQUIRED_PERMISSIONS,
                    REQUEST_CODE_PERMISSIONS
                )
            } else {
                val intent = Intent(this, CameraActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnGallery.setOnClickListener {
            Toast.makeText(this, getString(R.string.opening_gallery), Toast.LENGTH_SHORT).show()
            startGallery()
        }
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
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val selectedImage: Uri = it.data?.data as Uri
            val myFile = fromUriToFile(selectedImage, this)
            val intent = Intent(this, SnapFoodResultActivity::class.java)
            intent.putExtra(SnapFoodResultActivity.EXTRA_PICTURE, myFile)
            intent.putExtra(SnapFoodResultActivity.EXTRA_IS_FROM_GALLERY, true)
            startActivity(intent)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    getString(R.string.permission_not_granted),
                    Toast.LENGTH_SHORT
                ).show()
                this.finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            this.baseContext,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

}