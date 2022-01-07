package com.example.appparking

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_ajustes_1.*
import kotlinx.android.synthetic.main.item_ajustes_2.*
import kotlinx.android.synthetic.main.item_ajustes_3.*
import kotlinx.android.synthetic.main.activity_datos_usuario.*

class DatosUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_usuario)

        cargarAutoCompleteTextViewProvincia()
        cargarAutoCompleteTextViewMarca()
        cargarDatosUsuario()

        var nombre = ""
        var ciudad = ""
        var marca = ""

        val botonAceptar = findViewById<CardView>(R.id.botonAceptar)
        botonAceptar.setOnClickListener {
            if (editTextNombre.text.isEmpty() || editTextCiudad.text.isEmpty() || editTextMarca.text.isEmpty()) {
                Toast.makeText(this, "¡Has de rellenar todos los campos!", Toast.LENGTH_SHORT).show()
            } else {
                nombre = editTextNombre.text.toString()
                ciudad = editTextCiudad.text.toString()
                marca = editTextMarca.text.toString()

                val sharedPreferences : SharedPreferences = getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE)
                val editor : SharedPreferences.Editor = sharedPreferences.edit()
                editor.apply {
                    putString("nombre", nombre)
                    putString("ciudad", ciudad)
                    putString("marca", marca)
                }.apply()

                val volverMain = Intent(this, MainActivity::class.java)
                startActivity(volverMain)
            }
        }
    }

    private fun cargarAutoCompleteTextViewMarca() {
        val marcas = resources.getStringArray(R.array.marcascoche)
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_dropdown_item_1line, marcas)
        editTextMarca.setAdapter(adapter)
    }

    private fun cargarAutoCompleteTextViewProvincia() {
        val provincias = resources.getStringArray(R.array.provincias)
        val adapter = ArrayAdapter(this,
        android.R.layout.simple_dropdown_item_1line, provincias)
        editTextCiudad.setAdapter(adapter)
    }

    private fun cargarDatosUsuario() {
        /**
         * Parámetros de main_activity
         * @param textNombreAjustes : TextView
         * @param textCiudadAjustes : TextView
         * @param textMarcaAjustes : TextView
         */
        textoNombreAjustes.text = cargarNombreUsuario()
        textoCiudadAjustes.text = cargarCiudadUsuario()
        textoMarcaAjustes.text = cargarMarcaUsuario()
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

