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
import com.bangkit.bahanbaku.core.utils.addressObjectToString
import com.bangkit.bahanbaku.databinding.ActivityAddressMapsBinding
import com.bangkit.bahanbaku.presentation.addressmapsdetails.AddressMapsDetailsActivity
import com.bangkit.bahanbaku.presentation.checkout.CheckoutActivity
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

    private val recipe = MutableLiveData<CheckoutDataClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_address) as SupportMapFragment
        mapFragment.getMapAsync(this)

        recipe.postValue(intent.getParcelableExtra(CheckoutActivity.EXTRA_RECIPE))

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
            val addressData = AddressInput(
                zipCode = if (zipCode.isEmpty()) 0 else zipCode.toInt(),
                province = address.adminArea ?: "",
                city = address.subAdminArea ?: "",
                street = "${address.thoroughfare ?: ""} ${address.featureName ?: ""}",
                latitude = address.latitude,
                district = address.locality ?: "",
                label = "",
                longitude = address.longitude,
                receiverName = if (profile != null) this.getString(R.string.format_name)
                    .format(profile!!.firstName, profile!!.lastName) else "",
                receiverPhoneNumber = if (profile != null) profile!!.phoneNumber else ""
            )

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
                    val addressData = AddressInput(
                        zipCode = if (zipCode.isEmpty()) 0 else zipCode.toInt(),
                        province = data.adminArea ?: "",
                        city = data.subAdminArea ?: "",
                        street = "${data.thoroughfare ?: ""} ${data.featureName ?: ""}",
                        latitude = data.latitude,
                        district = data.locality ?: "",
                        label = "",
                        longitude = data.longitude,
                        receiverName = if (profile != null) this.getString(R.string.format_name)
                            .format(profile!!.firstName, profile!!.lastName) else "",
                        receiverPhoneNumber = if (profile != null) profile!!.phoneNumber else ""
                    )
                    addressLiveData.postValue(addressData)
                }
            }
        } else {
            val address = geocoder.getFromLocation(loc.latitude, loc.longitude, 1)
            if (address != null) {
                val data = address[0]
                val addressData = AddressInput(
                    zipCode = (data.postalCode ?: "").toInt(),
                    province = data.adminArea ?: "",
                    city = data.subAdminArea ?: "",
                    street = "${data.thoroughfare ?: ""} ${data.featureName ?: ""}",
                    latitude = data.latitude,
                    district = data.locality,
                    label = "",
                    longitude = data.longitude,
                    receiverName = "",
                    receiverPhoneNumber = ""
                )
                addressLiveData.postValue(addressData)
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

    companion object {
        const val TAG = "AddressMapsActivity"
    }
}