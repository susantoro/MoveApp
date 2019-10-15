package com.susanatoro.moveapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import kotlinx.android.synthetic.main.activity_registro3.*

class Registro3Activity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private var nombre = "susana"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro3)

        auth = FirebaseAuth.getInstance()

        val intent =intent

        var correo = intent.getStringExtra("correo")!!


        nombre = intent.getStringExtra("nombre")!!


        tvNombre.text = "Bienvenido $nombre!"+"\n correo: $correo"

        bnContinuar.setOnClickListener{


             val username = etUsuario.text.toString()
             val contrasenia = etPass.text.toString()
             val repiteContrasenia= etPass2.text.toString()

            if(username!="" && contrasenia!="" && repiteContrasenia!="") {
                if(contrasenia==repiteContrasenia) {
                    auth.createUserWithEmailAndPassword(correo, contrasenia)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                updateUI(user)

                                Toast.makeText(this, "Te has registado con éxito!", Toast.LENGTH_SHORT).show()

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(this, "El registro ha fallado. Intentalo de nuevo.", Toast.LENGTH_SHORT).show()

                            }

                        }
                }else{
                    Toast.makeText(baseContext, "Las contrasenias no coinciden",Toast.LENGTH_SHORT).show()

                }
            }else{
                Toast.makeText(baseContext, "Uno o más campos están vacíos",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser!=null){

            val intent = Intent(this,Registro4Activity::class.java)
            intent.putExtra("nombre",nombre)
            startActivity(intent)
            Toast.makeText(this,"Te has registado con éxito!",Toast.LENGTH_SHORT).show()
            finish()
        }else{
            Toast.makeText(this, "Falló el registro.Intentalo de nuevo",Toast.LENGTH_SHORT).show()
        }
    }
}
