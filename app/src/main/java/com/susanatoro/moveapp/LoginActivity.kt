package com.susanatoro.moveapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        auth = FirebaseAuth.getInstance()

        bnIniciarSesion.setOnClickListener {
            if(etCorreo.text.toString()!="" && etContraseña.text.toString()!="") {
                auth.signInWithEmailAndPassword(etCorreo.text.toString(), etContraseña.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            updateUI(user)
                        } else {
                            updateUI(null)
                        }
                    }
            }else{
                Toast.makeText(baseContext, "Falló la autenticación.", Toast.LENGTH_SHORT).show()
            }
        }

        tvRegistrar.setOnClickListener(){
            startActivity(Intent(this,RegistroActivity::class.java))
            finish()
        }
    }
    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser!=null){

            startActivity(Intent(this,NavegationDrawer::class.java))
            Toast.makeText(baseContext, "Iniciando sesión...",Toast.LENGTH_SHORT).show()
            finish()
        }else{
            Toast.makeText(baseContext, "Falló la autenticación.",Toast.LENGTH_SHORT).show()
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
}
