package com.susanatoro.moveapp

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_miservicio.*
import java.io.IOException

class NavegationDrawer : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var auth:FirebaseAuth
    private var descripcion=""
    private var tarifaUsuario=""
    private var nombre="susana"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navegation_drawer)

        auth = FirebaseAuth.getInstance()


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

//        val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        val miServicioFragment = MiservicioFragment()
        nombre = intent.getStringExtra("nombre")!! //Se necesita enviar al fragment de miServicio y completar para la base de datos

        val args = Bundle()
        args.putString("nombre",nombre)
        miServicioFragment.arguments = args

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.contenedor, miServicioFragment)
        transaction.commit()

    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.navegation_drawer, menu)
//        return true
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        // Handle navigation view item clicks here.

        when (item.itemId) {
            R.id.perfil -> {
                val miperfilFragment=PerfilFragment()
                val args = Bundle()
                args.putString("nombre",nombre)
                miperfilFragment.arguments = args
                transaction.replace(R.id.contenedor,miperfilFragment).commit()
            }
            R.id.servicio -> {
                val miservicioFragment=MiservicioFragment()
                val args = Bundle()
                args.putString("nombre",nombre)
                miservicioFragment.arguments = args
                transaction.replace(R.id.contenedor,miservicioFragment).commit()

                Toast.makeText(this,"presionastes enviar",Toast.LENGTH_SHORT).show()
            }

            R.id.salir -> {
                startActivity(Intent(this,LoginActivity::class.java))
                auth.signOut() //Cerrar sesión
                finish()
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
