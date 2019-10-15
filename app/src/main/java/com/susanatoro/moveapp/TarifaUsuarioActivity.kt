package com.susanatoro.moveapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_tarifa_usuario.*

class TarifaUsuarioActivity : AppCompatActivity() {

    private var tarifaCliente=""
    private var nombre = "susaalex"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarifa_usuario)



        bnContinuar.setOnClickListener {
            nombre = intent.getStringExtra("nombre")!!
            val intentDescripcion = intent
            tarifaCliente = etTarifaUsuario.text.toString()
            if(tarifaCliente!=""){

                var tipoVehiculo = intentDescripcion.getStringExtra("tipoVehiculo")
                var descripcion = intentDescripcion.getStringExtra("descripcion")
                tvTarifa.text = "tipo de vehiculo: "+tipoVehiculo+"\ntarifa: "+tarifaCliente+"\nDescripcion: "+descripcion+"\nnombre: "+nombre

                val refCliente = FirebaseDatabase.getInstance().getReference("cliente")
                val id = refCliente.push().key
                val cliente = Cliente(id!!,nombre,tarifaCliente,tipoVehiculo!!,"","",descripcion!!)

                refCliente.child(nombre).setValue(cliente).addOnSuccessListener {
                    Toast.makeText(this,"Información guardada exitosamente",Toast.LENGTH_SHORT).show()
                }

                val intent = Intent(this,NavegationDrawer::class.java)
                intent.putExtra("nombre",nombre)
                startActivity(intent)

            }else{
                Toast.makeText(this,"Ingrese la tarifa que estaría dispuest@ a pagar",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
