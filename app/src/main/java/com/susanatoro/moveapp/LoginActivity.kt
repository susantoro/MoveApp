package com.susanatoro.moveapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        bnIniciarSesion.setOnClickListener {
            startActivity(Intent(this,NavigationActivity::class.java))
        }

        tvRegistrar.setOnClickListener(){
            startActivity(Intent(this,RegistroActivity::class.java))
            finish()
        }
    }
}
