package com.example.login_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_juego_p_p_t.*
import java.util.*

class JuegoPPTActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego_p_p_t)
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val servicio = bundle?.getInt("servicio")
        if  (servicio == 0) {
            textViewUsuario.text = "Basico: " + email
        } else {
            textViewUsuario.text = "Google: " + email
        }

        //numero = 0 Piedra || numero = 1 Papel || numero = 2 Tijeras
        buttonVS.setOnClickListener {
            val numero: Int = ((Math.random() * 3).toInt())

            var puntosTotalesPersonaje = textViewPuntosPersonaje.text.toString().toInt()
            var puntosTotalesBot = textViewPuntosBot.text.toString().toInt()

            if (numero == 0) {
                textViewMano.text = "Piedra"
                if (radioButtonPapel.isChecked) {
                    puntosTotalesPersonaje++
                    textViewPuntosPersonaje.text = puntosTotalesPersonaje.toString()
                } else {
                    if (radioButtonTijeras.isChecked) {
                        puntosTotalesBot++
                        textViewPuntosBot.text = puntosTotalesBot.toString()
                    }
                }
            } else {
                if (numero == 1) {
                    textViewMano.text = "Papel"
                    if (radioButtonPiedra.isChecked) {
                        puntosTotalesBot++
                        textViewPuntosBot.text = puntosTotalesBot.toString()
                    } else {
                        if (radioButtonTijeras.isChecked) {
                            puntosTotalesPersonaje++
                            textViewPuntosPersonaje.text = puntosTotalesPersonaje.toString()
                        }
                    }
                } else {
                    if (numero == 2 || numero == 3) {
                        textViewMano.text = "Tijeras"
                        if (radioButtonPiedra.isChecked) {
                            puntosTotalesPersonaje++
                            textViewPuntosPersonaje.text = puntosTotalesPersonaje.toString()
                        } else {
                            if (radioButtonPapel.isChecked) {
                                puntosTotalesBot++
                                textViewPuntosBot.text = puntosTotalesBot.toString()
                            }
                        }
                    }
                }
            }
        }

    }


}