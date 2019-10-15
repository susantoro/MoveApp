package com.susanatoro.moveapp

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_ubicacion.*

class UbicacionActivity : AppCompatActivity() {

    private var flagUbicacion = false

    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE=1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubicacion)


        setUpMap()

        bnSiguiente.setOnClickListener{


            startActivity(Intent(this, Registro2Activity::class.java))
            finish()

        }
    }

    private fun setUpMap(){
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
    }

}
