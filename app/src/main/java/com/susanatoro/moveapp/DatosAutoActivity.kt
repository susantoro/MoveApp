package com.susanatoro.moveapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_datos_auto.*

class DatosAutoActivity : AppCompatActivity() {

    private var tipoVehiculo = ""
    private var nombre="susana"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_auto)


        bnContinuar.setOnClickListener {
            //nombre = intent.getStringExtra("nombre")!!

            if(tipoVehiculo!="") {
                val intent = Intent(this, DescripcionServicioActivity::class.java)
                intent.putExtra("tipoVehiculo", tipoVehiculo)
                intent.putExtra("nombre",nombre)
                startActivity(intent)
            }else{
                Toast.makeText(this,"Elige una de las opciones porfavor",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.rbnMoto ->
                    if (checked) {
                     tipoVehiculo = rbnMoto.text.toString()
                    }
                R.id.rbnCamion->
                    if (checked) {
                        tipoVehiculo = rbnCamion.text.toString()
                    }
                R.id.rbnCamioneta->
                    if (checked) {
                        tipoVehiculo = rbnCamioneta.text.toString()
                    }
                R.id.rbnNolose->{
                    if(checked){
                        tipoVehiculo = rbnNolose.text.toString()
                    }
                }
            }
        }
    }

}
