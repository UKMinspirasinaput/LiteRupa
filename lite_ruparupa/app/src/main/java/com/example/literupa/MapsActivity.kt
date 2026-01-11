package com.example.literupa

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Setup fragment peta
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val btnKonfirmasi = findViewById<Button>(R.id.btnKonfirmasiLokasi)
        btnKonfirmasi.setOnClickListener {
            Toast.makeText(this, "Alamat Pengiriman Disimpan!", Toast.LENGTH_LONG).show()
            finish() // Selesai dan balik ke Login
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Lokasi Default (Misal: Jakarta)
        val defaultLoc = LatLng(-6.2000, 106.8166)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLoc, 15f))

        // Klik peta untuk pilih lokasi
        mMap.setOnMapClickListener { latLng ->
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(latLng).title("Lokasi Terpilih"))
            findViewById<TextView>(R.id.tvInfoLokasi).text =
                "Lokasi Terpilih: ${latLng.latitude}, ${latLng.longitude}"
        }
    }
}
