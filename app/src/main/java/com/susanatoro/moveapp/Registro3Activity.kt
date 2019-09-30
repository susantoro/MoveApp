package com.susanatoro.moveapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_registro3.*

class Registro3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro3)

        bnContinuar.setOnClickListener(){

            startActivity(Intent(this,Registro4Activity::class.java))
            finish()
        }
    }
}
