package com.bangkit.bahanbaku.presentation.addressmaps

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.data.Resource
import com.bangkit.bahanbaku.core.domain.model.AddressInput
import com.bangkit.bahanbaku.core.domain.model.CheckoutDataClass
import com.bangkit.bahanbaku.core.domain.model.Profile
import com.bangkit.bahanbaku.core.utils.DataMapper
import com.bangkit.bahanbaku.core.utils.ERROR_DEFAULT_MESSAGE
import com.bangkit.bahanbaku.core.utils.addressObjectToString
import com.bangkit.bahanbaku.databinding.ActivityAddressMapsBinding
import com.bangkit.bahanbaku.presentation.addressmapsdetails.AddressMapsDetailsActivity
import com.bangkit.bahanbaku.presentation.checkout.CheckoutActivity
import com.bangkit.bahanbaku.presentation.directpayment.DirectPaymentActivity
import com.bangkit.bahanbaku.presentation.login.LoginActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.*

@AndroidEntryPoint
class AddressMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val binding: ActivityAddressMapsBinding by lazy {
        ActivityAddressMapsBinding.inflate(layoutInflater)
    }

    private val viewModel: AddressMapsViewModel by viewModels()

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var location: Location? = null
    private val addressLiveData = MutableLiveData<AddressInput>()
    private var profile: Profile? = null
    private lateinit var recipeName: String
    private lateinit var recipeId: String

    private val recipe = MutableLiveData<CheckoutDataClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_address) as SupportMapFragment
        mapFragment.getMapAsync(this)

        recipe.postValue(intent.getParcelableExtra(CheckoutActivity.EXTRA_RECIPE))
        recipeName = intent.getStringExtra(CheckoutActivity.EXTRA_FOOD_NAME) ?: ""
        recipeId = intent.getStringExtra(DirectPaymentActivity.EXTRA_RECIPE_ID) ?: ""

        init()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun init() {
        binding.etAddressSearch.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || keyEvent.action == KeyEvent.ACTION_DOWN
                || keyEvent.action == KeyEvent.KEYCODE_ENTER
            ) {
                geoLocate()
            }

            false
        }
    }

    private fun geoLocate() {
        val geocoder = Geocoder(this)
        val addressList = ArrayList<Address>()

        try {
            addressList.addAll(
                geocoder.getFromLocationName(
                    binding.etAddressSearch.text.toString(),
                    1
                ) as Collection<Address>
            )
        } catch (e: IOException) {
            Log.e(TAG, e.message.toString())
        }

        focusToAddress(addressList)
    }

    private fun geoLocateFromLatLng(latLng: LatLng) {
        val geocoder = Geocoder(this)
        val addressList = ArrayList<Address>()

        try {
            addressList.addAll(
                geocoder.getFromLocation(
                    latLng.latitude,
                    latLng.longitude,
                    1
                ) as Collection<Address>
            )
        } catch (e: IOException) {
            Log.e(TAG, e.message.toString())
        }

        focusToAddress(addressList)
    }

    private fun focusToAddress(addressList: List<Address>) {
        if (addressList.isNotEmpty()) {
            val address = addressList[0]
            val loc = LatLng(address.latitude, address.longitude)
            mMap.clear()
            mMap.addMarker(
                MarkerOptions()
                    .position(loc)
                    .title(getString(R.string.youre_here))
            )

            Log.d("TEST_LOCATION", addressList[0].toString())
            val zipCode = address.postalCode ?: ""
            val addressData = DataMapper.mapAddressToInputAddress(this, address, zipCode, profile)

            addressLiveData.postValue(addressData)

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15f))
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getToken()
    }

    private fun getLastLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                this.location = location
                showCurrentLocation(location)

                setupButtons()
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun setupButtons() {
        binding.btnGetLocationNow.setOnClickListener {
            getLastLocation()
        }

        binding.btnProceedAddress.setOnClickListener {
            if (addressLiveData.value != null) {
                val intent = Intent(this, AddressMapsDetailsActivity::class.java)
                intent.putExtra(AddressMapsDetailsActivity.EXTRA_ADDRESS, addressLiveData.value)
                intent.putExtra(CheckoutActivity.EXTRA_RECIPE, recipe.value)
                intent.putExtra(
                    CheckoutActivity.EXTRA_FOOD_NAME,
                    recipeName
                )
                intent.putExtra(
                    DirectPaymentActivity.EXTRA_RECIPE_ID,
                    recipeId
                )
                startActivity(intent)
            }
        }
    }

    private fun showCurrentLocation(location: Location) {
        val loc = LatLng(location.latitude, location.longitude)
        mMap.addMarker(
            MarkerOptions()
                .position(loc)
                .title(getString(R.string.youre_here))
        )

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15f))

        val geocoder = Geocoder(this, Locale.getDefault())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(loc.latitude, loc.longitude, 1) { addressList ->
                if (profile == null) {
                    val data = addressList[0]
                    val zipCode = data.postalCode ?: ""
                    val addressData =
                        DataMapper.mapAddressToInputAddress(this, data, zipCode, profile)
                    addressLiveData.postValue(addressData)
                }
            }
        } else {
            try {
                val address = geocoder.getFromLocation(loc.latitude, loc.longitude, 1)
                if (address != null) {
                    val data = address[0]
                    val zipCode = data.postalCode ?: ""
                    val addressData =
                        DataMapper.mapAddressToInputAddress(this, data, zipCode, profile)
                    addressLiveData.postValue(addressData)
                }
            } catch (e: IOException) {
                Toast.makeText(this, ERROR_DEFAULT_MESSAGE, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    getLastLocation()
                }
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    getLastLocation()
                }
            }
        }

    private fun setupView(token: String) {
        viewModel.getProfile(token).observe(this) { result ->
            when (result) {
                is Resource.Error -> {
                    Toast.makeText(
                        this,
                        getString(R.string.error_loading_profile),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    val data = result.data
                    profile = data
                }
            }
        }

        mMap.setOnMapClickListener { latLng ->
            geoLocateFromLatLng(latLng)
        }

        getLastLocation()

        addressLiveData.observe(this) { address ->
            binding.tvAddressPreview.text = addressObjectToString(address)
        }
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
                setupView(token)
            }
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
        const val TAG = "AddressMapsActivity"
    }
}