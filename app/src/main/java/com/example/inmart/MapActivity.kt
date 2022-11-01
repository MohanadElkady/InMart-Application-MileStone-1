package com.example.inmart

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class MapActivity : AppCompatActivity(),OnMapReadyCallback , GoogleMap.OnMarkerClickListener {
    lateinit var mapfragment:SupportMapFragment
    lateinit var googlemap:GoogleMap
    lateinit var client:GoogleMap
    lateinit var lastlocation:Location
    lateinit var fusedclient: FusedLocationProviderClient

    companion object{
        private const val LOCATION_REQUEST_CODE =1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        mapfragment=supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapfragment.getMapAsync(this)
        fusedclient=LocationServices.getFusedLocationProviderClient(this)

       /* mapfragment.getMapAsync(OnMapReadyCallback {
            googlemap=it
            if(googlemap.isMyLocationEnabled) {
                val location1 = LatLng(13.03, 77.60)
                googlemap.addMarker(MarkerOptions().position(location1))
                googlemap.animateCamera(CameraUpdateFactory.newLatLngZoom(location1, 15f))
            }
        })*/
    }

    override fun onMapReady(p0: GoogleMap) {
        client=p0
        client.uiSettings.isZoomControlsEnabled=true
        client.setOnMarkerClickListener(this)

        setUpMap()
    }

    private fun setUpMap(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_REQUEST_CODE)

            return
        }
        client.isMyLocationEnabled=true
        fusedclient.lastLocation.addOnSuccessListener(this){

            if(it !=null){
                val initialLoc =client.getCameraPosition().target
                lastlocation=it
                val currLat=LatLng(it.latitude,it.longitude)
                placeMarker(currLat)
                client.animateCamera(CameraUpdateFactory.newLatLngZoom(currLat,19f))
            }
        }
    }

    private fun placeMarker(currLat: LatLng) {
        val markerOptions=MarkerOptions().position(currLat)
        markerOptions.title("$currLat")
        client.addMarker(markerOptions)
    }

    override fun onMarkerClick(p0: Marker?) = false
}