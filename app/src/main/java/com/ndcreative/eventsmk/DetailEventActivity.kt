package com.ndcreative.eventsmk

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_event.*

class DetailEventActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        txtEventName.text = intent.getStringExtra("event_name")
        txtEventDate.text = intent.getStringExtra("event_date")
        txtEventDesc.text = intent.getStringExtra("event_desc")
        Picasso.get().load(intent.getStringExtra("img_poster")).into(imgEvent)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fabShareEvent.setOnClickListener {
            val shareIntent = Intent()

            val latitude = intent.getDoubleExtra("latitude", 0.0)
            val longitude = intent.getDoubleExtra("longitude", 0.0)
            val name = intent.getStringExtra("event_name")

            val googleMapsLink = "http://maps.google.com/maps?daddr=" + latitude + "," + longitude
            val content = "Mari kunjungi " + name + "\n" + googleMapsLink

            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, content)
            startActivity(Intent.createChooser(shareIntent, "Bagikan ke"))
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val lokasiEvent = LatLng(
            intent.getDoubleExtra("latitude", 0.0),
            intent.getDoubleExtra("longitude", 0.0)
        )
        val cameraPosition = CameraPosition.builder()
            .target(lokasiEvent)
            .zoom(17f)
            .bearing(90f)
            .tilt(30f)
            .build()

        googleMap?.addMarker(MarkerOptions().position(lokasiEvent))
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(lokasiEvent))
        googleMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

    }
}
