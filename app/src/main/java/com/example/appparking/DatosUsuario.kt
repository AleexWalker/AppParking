package com.example.appparking

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_ajustes_1.*
import kotlinx.android.synthetic.main.item_ajustes_2.*
import kotlinx.android.synthetic.main.item_ajustes_3.*
import kotlinx.android.synthetic.main.activity_datos_usuario.*
import java.io.Serializable

class DatosUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_usuario)

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

                val sharedPreferences : SharedPreferences = getSharedPreferences("datosUsuario", Context.MODE_PRIVATE)
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
}

