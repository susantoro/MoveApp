package com.susanatoro.moveapp

import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_maps.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        var intent = intent
        var direccionUbicacionOrigen = intent.getStringExtra("direccionOrigen")!!
        var direccionUbicacionDestino = intent.getStringExtra("direccionDestino")!!

        tvOrigen.text = direccionUbicacionOrigen
        tvDestino.text = direccionUbicacionDestino

        tvResult.text = "direcion origen: "+direccionUbicacionOrigen+"\ndireccion destino: "+direccionUbicacionDestino

        mMap = googleMap
        mMap.getUiSettings().setZoomControlsEnabled(true)
        //setUpMapMiUbicacion()

        var geocoder = Geocoder(this)
        lateinit var listOrigen : MutableList<Address>
        lateinit var listDestino : MutableList<Address>


        try {
            listOrigen = geocoder.getFromLocationName(direccionUbicacionOrigen,1)
            listDestino = geocoder.getFromLocationName(direccionUbicacionDestino,1)

        }catch (e: IOException){

        }
        if (listOrigen.size > 0 && listDestino.size>0) {

            var addressOrigen = listOrigen.get(0)
            var addressDestino = listDestino.get(0)
            var positionOrigen = LatLng(addressOrigen.latitude, addressOrigen.longitude)
            var positionDestino = LatLng(addressDestino.latitude, addressDestino.longitude)
            var markerOrigen = MarkerOptions().title(direccionUbicacionOrigen).position(positionOrigen)
            var markerDestino = MarkerOptions().title(direccionUbicacionDestino).position(positionDestino)
            mMap.addMarker(markerOrigen)
            mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(positionOrigen, 15F)
            )
            mMap.addMarker(markerDestino)
            mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(positionDestino, 15F)
            )
            val URL = getDirectionURL(positionOrigen,positionDestino)
            GetDirection(URL).execute()
        } else
            Toast.makeText(this, "Direccion no encontrada", Toast.LENGTH_SHORT).show()

    }

    private fun getDirectionURL(origin:LatLng,dest:LatLng):String{
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}&destination=${dest.latitude},${dest.longitude}&key=AIzaSyBoV3rGS2S-f_8-FB5bHa23f9Buhdx5f4I&mode=driving"
    }

    private fun setUpMapMiUbicacion() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        //mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLng)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }
    private fun placeMarkerOnMap(location: LatLng) {
        val markerOptions = MarkerOptions().position(location)
        markerOptions.title("Estoy aquí!!")
        markerOptions.icon(
            BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(resources, R.mipmap.ic_user_location)))
        mMap.addMarker(markerOptions)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
    }

    inner class GetDirection(val url:String) : AsyncTask<Void, Void, List<List<LatLng>>>(){
        override fun doInBackground(vararg p0: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data  = response.body()!!.string()

            val result = ArrayList<List<LatLng>>()

            try{

                val respObj = Gson().fromJson(data,GoogleMapDTO::class.java)
                val path = ArrayList<LatLng>()

                for(i in 0..(respObj.routes[0].legs[0].steps.size-1)){
                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)


            }catch (e:Exception){
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>?) {
            val lineoption = PolylineOptions()
            for (i in result!!.indices){
                lineoption.addAll(result[i])
                lineoption.width(5f)
                lineoption.color(Color.BLUE)
                lineoption.geodesic(true)
            }
            mMap.addPolyline(lineoption)
        }

    }

    fun decodePolyline(encoded: String): List<LatLng> {

        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val latLng = LatLng((lat.toDouble() / 1E5),(lng.toDouble() / 1E5))
            poly.add(latLng)
        }

        return poly
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}
