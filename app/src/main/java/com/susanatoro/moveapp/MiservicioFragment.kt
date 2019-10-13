package com.susanatoro.moveapp


import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_miservicio.*
import kotlinx.android.synthetic.main.fragment_miservicio.view.*
import kotlinx.android.synthetic.main.fragment_miservicio.view.atDestino
import kotlinx.android.synthetic.main.fragment_miservicio.view.atOrigen
import kotlinx.android.synthetic.main.fragment_miservicio.view.bnEnviar
import java.io.IOException

class MiservicioFragment : Fragment() {

    private lateinit var root:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root=inflater.inflate(R.layout.fragment_miservicio, container, false)

        root.bnEnviar.setOnClickListener {

            var ubicacionOrigen = atOrigen.text.toString()
            var ubicacionDestino = atDestino.text.toString()

            val intent = Intent(root.context,MapsActivity::class.java)
            intent.putExtra("direccionOrigen",ubicacionOrigen)
            intent.putExtra("direccionDestino",ubicacionDestino)
            startActivity(intent)
        }



        return root
    }


}
