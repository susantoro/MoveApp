package com.susanatoro.moveapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registro2.*


import java.util.ArrayList
import java.util.HashMap

class Registro2Activity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    internal var expandableListView: ExpandableListView? = null
    internal var adapter: ExpandableListAdapter? = null
    internal var titleList: List<String> ? = null
    private lateinit var Ciudades:ArrayList<String>

    private var cont=0

    /*val data: HashMap<String, List<String>>
        get() {
            val listData = HashMap<String, List<String>>()

            Ciudades = ArrayList<String>()
            Ciudades.add("Medellin")
            Ciudades.add("Bogota")
            Ciudades.add("Pereira")


            listData["Ciudad"] = Ciudades

            return listData
        }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro2)

        auth = FirebaseAuth.getInstance()


        bnSiguiente.setOnClickListener{
            var name=etUsername.text.toString()
            var apellido=etUserlast.text.toString()
            var email=etUseremail.text.toString()
            var ciudad=etCiudad.text.toString()
            var foto="urlDelaFoto"


            if(name!="" && apellido!="" && email!="" && ciudad!="") { //foto opcional por el momento
                cont++

                val ref = FirebaseDatabase.getInstance().getReference("usuario")

                val usuario = Usuario("usuario $cont",name,apellido,email,ciudad,foto)

                ref.child("usuario $cont").setValue(usuario)

                /*val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("message")

                myRef.setValue("Hello, World!")*/
                Toast.makeText(this,"pasando información a la base de datos",Toast.LENGTH_SHORT).show()

                val intent = Intent(this,Registro3Activity::class.java)
                intent.putExtra("nombre",name)
                intent.putExtra("correo",email)
                intent.putExtra("cont",cont)
                startActivity(intent)
                //finish()
            }else{
                Toast.makeText(baseContext, "Falló la autenticación.",Toast.LENGTH_SHORT).show()
            }

        }


        /*expandableListView = findViewById(R.id.elUsercity)
        if (expandableListView != null) {
            val listData = data
            titleList = ArrayList(listData.keys)
            adapter = CustomExpandableListAdapter(this, titleList as ArrayList<String>, listData)
            expandableListView!!.setAdapter(adapter)

            expandableListView!!.setOnGroupExpandListener { groupPosition ->
                Toast.makeText(
                    applicationContext,
                    (titleList as ArrayList<String>)[groupPosition] + " List Expanded.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            expandableListView!!.setOnGroupCollapseListener { groupPosition ->
                Toast.makeText(
                    applicationContext,
                    (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            expandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
                Toast.makeText(
                    applicationContext,
                    "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(titleList as ArrayList<String>)[groupPosition]]!!.get(
                        childPosition
                    ),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
        }*/

    }
}
