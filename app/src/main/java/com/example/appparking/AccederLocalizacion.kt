package com.example.appparking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.appparking.databinding.ActivityAccederLocalizacionBinding

class AccederLocalizacion : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityAccederLocalizacionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAccederLocalizacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val bundle = intent.extras
        val latitudGuardada = bundle?.getDouble("latitud")
        val longitudGuardada = bundle?.getDouble("longitud")

        if (latitudGuardada != null && longitudGuardada != null) {
            val localizacion = LatLng(latitudGuardada, longitudGuardada)
            mMap.addMarker(MarkerOptions().position(localizacion).title("Marker in Sydney"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(localizacion))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(localizacion, 19f))
        } else {
            Toast.makeText(this, "¡Error al guardar coordenadas, prueba de nuevo!", Toast.LENGTH_SHORT).show()
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}