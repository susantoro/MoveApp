package com.susanatoro.moveapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_registro4.*

class Registro4Activity : AppCompatActivity() {

    //private var nombre="susana"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro4)

        bnContinuar.setOnClickListener{

            //nombre = intent.getStringExtra("nombre")!!

            if(rbnConductor.isChecked){


            }
            if(rbnCliente.isChecked){
                val intent = Intent(this,DatosAutoActivity::class.java)
                //intent.putExtra("nombre",nombre)
                startActivity(intent)
                finish()
            }
        }
    }
}
