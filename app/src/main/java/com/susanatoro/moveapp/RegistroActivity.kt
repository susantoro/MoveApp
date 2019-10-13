package com.susanatoro.moveapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_registro.*
import java.util.concurrent.TimeUnit

class RegistroActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var callBackPhone:PhoneAuthProvider.OnVerificationStateChangedCallbacks

    var verificacionId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        auth = FirebaseAuth.getInstance()

        bnTelefono.setOnClickListener {
            verificacion()
            startActivity(Intent(this,UbicacionActivity::class.java))
        }



        bnVerificar.setOnClickListener {

            autenticacionPhone()

        }
    }

    private fun autenticacionPhone() {
        var verificarNumero = etVerificar.text.toString()

        val credential = PhoneAuthProvider.getCredential(verificacionId,verificarNumero)
        SignInPhone(credential)
    }

    private fun verificacion() {

        verificationCallbacks()

        val phoneNumber = "+57"+etPhone.text.toString()

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            this,
            callBackPhone
        )
    }
    private fun verificationCallbacks(){
        callBackPhone = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                SignInPhone(credential)
            }

            override fun onVerificationFailed(p0: FirebaseException) {

            }

            override fun onCodeSent(verification: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verification, p1)

                verificacionId = verification
            }

        }
    }
    private fun SignInPhone(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")

                    val user = task.result?.user
                    updateUI(user)
                    // ...
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this,"Falló la utenticación",Toast.LENGTH_SHORT).show()
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                }
            }
    }
    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser!=null){
            startActivity(Intent(this,UbicacionActivity::class.java))
            Toast.makeText(baseContext, "Verificación de teléfono correcta!",Toast.LENGTH_SHORT).show()
            finish()
        }else{
            Toast.makeText(baseContext, "Falló la autenticación.",Toast.LENGTH_SHORT).show()

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }

    companion object {
        private const val TAG = "FacebookLogin"
    }

}




