package com.example.appparking

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat

import kotlinx.android.synthetic.main.activity_google_maps.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.appparking.databinding.ActivityGoogleMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.Marker

class GoogleMaps : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityGoogleMapsBinding
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        const val LOCATION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGoogleMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        item_google_maps.setOnClickListener {

            val currentLat : Double = lastLocation.latitude
            val currentLng : Double = lastLocation.longitude

            val datosLatLng = Intent(this, MainActivity::class.java)
            datosLatLng.putExtra("latitud", currentLat)
            datosLatLng.putExtra("longitud", currentLng)

            startActivity(datosLatLng)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)
        setupMap()
    }

    private fun setupMap() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
            return
        }
        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->

            if (location != null) {
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)

                placeMarkerOnMap(currentLatLong)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 18f))
            }
        }
    }

    private fun placeMarkerOnMap(currentLatLong: LatLng) {
        val markerOptions = MarkerOptions().position(currentLatLong)
        markerOptions.title("$currentLatLong")
        mMap.addMarker(markerOptions)
    }

    override fun onMarkerClick(p0: Marker) = false
}


/**
 *
 * isLocationPermissionGranted()
 *
 * private fun isLocationPermissionGranted(): Boolean {
return if (ActivityCompat.checkSelfPermission(
this,
android.Manifest.permission.ACCESS_COARSE_LOCATION
) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
this,
android.Manifest.permission.ACCESS_FINE_LOCATION
) != PackageManager.PERMISSION_GRANTED
) {
val requestcode = 0
ActivityCompat.requestPermissions(
this,
arrayOf(
android.Manifest.permission.ACCESS_FINE_LOCATION,
android.Manifest.permission.ACCESS_COARSE_LOCATION
),
requestcode
)
false
} else {
true
}

}


val layoutGuardarLocalizacion = findViewById<ConstraintLayout>(R.id.item_google_maps)
layoutGuardarLocalizacion.setOnClickListener {
val coordenadasIntent = Intent(this, MainActivity::class.java)
//coordenadasIntent.putExtra("Latitud",  )
}

val datosLatLng = Intent(this, MainActivity::class.java)
datosLatLng.putExtra("latitud", location.latitude)
datosLatLng.putExtra("longitud", location.longitude)
 */
