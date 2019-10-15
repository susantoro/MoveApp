package com.susanatoro.moveapp

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_registro2.*
import java.util.*

class Registro2Activity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    internal var expandableListView: ExpandableListView? = null
    internal var adapter: ExpandableListAdapter? = null
    internal var titleList: List<String> ? = null
    private lateinit var Ciudades:ArrayList<String>
    private var foto=""
    val REQUEST_IMAGE_CAPTURE = 1
    val TOMAR_FOTO = 123
    var fotoSeleccionadaUri:Uri? = null
    var fotoURL="url foto"

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


            if(name!="" && apellido!="" && email!="" && ciudad!="" && fotoURL!="") { //foto opcional por el momento


                val ref = FirebaseDatabase.getInstance().getReference("usuario")

                val usuario = Usuario("usuario",name,apellido,email,ciudad,fotoURL)

                ref.child("usuario").setValue(usuario)

                /*val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("message")

                myRef.setValue("Hello, World!")*/
                Toast.makeText(this,"pasando información a la base de datos",Toast.LENGTH_SHORT).show()

                val intent = Intent(this,Registro3Activity::class.java)
                intent.putExtra("nombre",name)
                intent.putExtra("correo",email)

                startActivity(intent)
                //finish()
            }else{
                Toast.makeText(baseContext, "Falló la autenticación.",Toast.LENGTH_SHORT).show()
            }

        }

        iAgregarFoto.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,456)
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



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==456 && resultCode == Activity.RESULT_OK && data!=null){
            Toast.makeText(this,"Foto seleccionada",Toast.LENGTH_SHORT).show()

            fotoSeleccionadaUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,fotoSeleccionadaUri)
            val bitmaptDrawable = BitmapDrawable()
            //iAgregarFoto.setBackgroundDrawable(bitmaptDrawable )
            iAgregarFoto.setImageBitmap(bitmap)

            tvAgregarGaleria.text = fotoSeleccionadaUri.toString()
            fotoURL = fotoSeleccionadaUri.toString()
            upLoadImageToFireBaseStorage()
        }
    }

    private fun upLoadImageToFireBaseStorage(){
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().reference
        ref.putFile(fotoSeleccionadaUri!!).addOnSuccessListener {

            ref.downloadUrl.addOnSuccessListener {
                fotoURL = it.toString()
                Toast.makeText(this,"ubicación del archivo: ${it}",Toast.LENGTH_LONG).show()
                //saveFotoToFireBaseDataBase()
            }
        }



    }

    private fun saveFotoToFireBaseDataBase() {

    }

}
