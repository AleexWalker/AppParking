package com.example.appparking

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_datos_usuario.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.linearLayoutPrincipal
import kotlinx.android.synthetic.main.item_1.*
import kotlinx.android.synthetic.main.item_2.*
import kotlinx.android.synthetic.main.item_3.*
import kotlinx.android.synthetic.main.item_4.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutInflater.inflate(R.layout.activity_main, linearLayoutPrincipal, false)

        cargarDatosUsuario()

        /**
         * Lanzamos la activity_datos_usuario para que nuestro cliente realice los cambios que considere necesarios
         */
        val datosUsuario = findViewById<CardView>(R.id.cardHeader)
        datosUsuario.setOnClickListener {
            val lanzarDatosUsuario = Intent(this, DatosUsuario::class.java)
            startActivity(lanzarDatosUsuario)
        }

        /**
         * Recogemos los datos de la Latitud y Longitud enviados desde la clase de GoogleMaps
         * @param latitudGuardada : Parámetro de tipo Double que guarda la Latiud
         * @param longitudGuardada : Parámetro de tipo Double que guarda la Longitud
         */
        var latitudGuardada = obtenerDatosLatitud()
        var longitudGuardada = obtenerDatosLongitud()

        if (latitudGuardada != null) {
            if (longitudGuardada != null) {
                guardarCoordenadas(latitudGuardada, longitudGuardada)
            }
        }

        /**
         * En cuanto el usuario interactue con dicho layout lo mandamos a la clase de GoogleMaps
         * @param layoutPrimero : Parámetro que hace referencia al Layout item_1 insertado en el activity_main.xml
         */
        val layoutPrimero = findViewById<ConstraintLayout>(R.id.primerItem)
        layoutPrimero.setOnClickListener {
            val intent = Intent(this, GoogleMaps::class.java)
            startActivity(intent)
        }

        /**
         * En cuanto el usuario interactue con dicho layout lo mandamos a la clase de GoogleMaps
         * @param layoutSegundo : Parámetro que hace referencia al Layout item_2 insertado en el activity_main.xml
         */
        val layoutSegundo = findViewById<ConstraintLayout>(R.id.segundoItem)
        layoutSegundo.setOnClickListener {

            latitudGuardada = cargarCoordenadasLatitud()?.toDouble()
            longitudGuardada = cargarCoordenadasLongitud()?.toDouble()

            if (latitudGuardada == null || longitudGuardada == null) {
                Toast.makeText(this, "¡Primero has de guardar tu ubicación actual!", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, AccederLocalizacion::class.java)
                if (latitudGuardada != null) {
                    intent.putExtra("latitud", latitudGuardada)
                }
                if (longitudGuardada != null) {
                    intent.putExtra("longitud", longitudGuardada)
                }
                startActivity(intent)
            }
        }

        /**
         * En cuanto el usuario interactue con dicho layout lo mandamos a la clase de Parking
         * @param layoutTercero : Parámetro que hace referencia al Layout item_3 insertado en el activity_main.xml
         */
        val layoutTercero = findViewById<ConstraintLayout>(R.id.tercerItem)
        layoutTercero.setOnClickListener {
            intent = Intent(this, ParkingLocation::class.java)
            startActivity(intent)
        }

        /**
         * En cuanto el usuario interactue con dicho layout lo mandamos a la clase de Pruebas
         * @param layoutTercero : Parámetro que hace referencia al Layout item_3 insertado en el activity_main.xml
         */
        val layoutCuarto = findViewById<ConstraintLayout>(R.id.cuartoItem)
        layoutCuarto.setOnClickListener {
            intent = Intent(this, Pruebas::class.java)
            startActivity(intent)
        }

        añadirHorasLayouts()
    }

    private fun cargarDatosUsuario() {
        /**
         * Parámetros de main_activity
         * @param textNombre : TextView
         * @param textCiudad : TextView
         * @param textMarca : TextView
         */
        textNombre.text = cargarNombreUsuario()
        textCiudad.text = cargarCiudadUsuario()
        textMarca.text = cargarMarcaUsuario()
    }

    private fun guardarCoordenadas(latitudGuardada : Double, longitudGuardada : Double) {
        val sharedPreferences : SharedPreferences = getSharedPreferences("coordenadasLatLng", Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply {
            putString("latitud", latitudGuardada.toString())
            putString("longitud", longitudGuardada.toString())
        }.apply()
    }

    private fun cargarCoordenadasLatitud(): String? {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("coordenadasLatLng", Context.MODE_PRIVATE)

        return sharedPreferences.getString("latitud", null)
    }

    private fun cargarCoordenadasLongitud(): String? {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("coordenadasLatLng", Context.MODE_PRIVATE)

        return sharedPreferences.getString("longitud", null)
    }

    private fun añadirHorasLayouts() {
        textTime_1.text = getCurrentTime()
        textTime_2.text = getCurrentTime()
        textTime_3.text = getCurrentTime()
        textTime_4.text = getCurrentTime()
    }

    private fun obtenerDatosLatitud(): Double? {
        val bundle = intent.extras
        return bundle?.getDouble("latitud")
    }

    private fun obtenerDatosLongitud(): Double? {
        val bundle = intent.extras
        return bundle?.getDouble("longitud")
    }

    private fun getCurrentTime(): String {
        return SimpleDateFormat("EEEE, HH:mm", Locale.getDefault()).format(Date())
    }

    private fun cargarNombreUsuario() : String? {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE)

        return sharedPreferences.getString("nombre", null)
    }

    private fun cargarCiudadUsuario() : String? {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE)

        return sharedPreferences.getString("ciudad", null)
    }

    private fun cargarMarcaUsuario() : String? {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE)

        return sharedPreferences.getString("marca", null)
    }
}