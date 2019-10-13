package com.susanatoro.moveapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_registro4.*

class Registro4Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro4)

        bnContinuar.setOnClickListener{

            if(rbnConductor.isChecked){


            }
            if(rbnCliente.isChecked){
                startActivity(Intent(this,NavegationDrawer::class.java))
                //Se debe obtner un abandera para saber si eligió cliente o conductor y así procesar los datos
                //correspondietnes para subirlos a la base de datos.
                finish()
            }
        }
    }
}
