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

            if(bnConductor.isChecked){


            }
            if(bnUsuario.isChecked){
                //startActivity(Intent(this,DrawerActivity::class.java))
                //finish()
            }
        }
    }
}
