package com.susanatoro.moveapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_descripcion_servicio.*

class DescripcionServicioActivity : AppCompatActivity() {

    private var descripcion = ""
    private var nombre = "susana"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descripcion_servicio)



        bnContinuar.setOnClickListener {
            nombre = intent.getStringExtra("nombre")!!
            descripcion=""

            if(chArmario.isChecked)
                descripcion+=chArmario.text
            if(chCama.isChecked)
                descripcion+=chCama.text
            if(chComedor.isChecked)
                descripcion+=chComedor.text
            if(chElectrodomesticos.isChecked)
                descripcion+=chElectrodomesticos.text
            if(chOficina.isChecked)
                descripcion+=chOficina.text
            if(chMaquinariaPesada.isChecked)
                descripcion+=chMaquinariaPesada.text
            if(chMaquinariaLiviana.isChecked)
                descripcion+=chMaquinariaLiviana.text

            descripcion+=etDescripcion.text.toString()


            if(descripcion!="") {
                val intentAuto = intent
                tvDescripcion.text = descripcion + "\n${intentAuto.getStringExtra("tipoVehiculo")}"

                val intent = Intent(this, TarifaUsuarioActivity::class.java)
                intent.putExtra("descripcion", descripcion)
                intent.putExtra("tipoVehiculo", intentAuto.getStringExtra("tipoVehiculo"))
                intent.putExtra("nombre",nombre)
                startActivity(intent)

                descripcion = ""
            }else{
                Toast.makeText(this,"Realiza alguna descripción del servicio porfavor",Toast.LENGTH_SHORT).show()
            }
        }


    }
}
