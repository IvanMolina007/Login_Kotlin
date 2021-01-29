package com.example.login_kotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.email_password.*

class Email_password : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.email_password)

        val analytics:FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Entró")
        analytics.logEvent("InitScreen", bundle)

        setup()

    }

    private fun setup() {
        buttonRegistrar.setOnClickListener {
            if (editTextEmail.text.isNotEmpty() && editTextPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(editTextEmail.text.toString(), editTextPassword.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful) {
                        showOK()
                    } else {
                        showError()
                    }
                }
            } else {
                Toast.makeText(this, "Uno de los campos esta vacio", Toast.LENGTH_LONG).show()
            }
        }

        buttonIniciar.setOnClickListener {
            if (editTextEmail.text.isNotEmpty() && editTextPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(editTextEmail.text.toString(), editTextPassword.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful) {
                        showOK()
                        persistencia()
                        sesion()
                        val nextActivityIntent = Intent(this, JuegoPPTActivity::class.java).apply {
                            putExtra("email", editTextEmail.text.toString())
                            putExtra("servicio", 0)
                        }
                        startActivity(nextActivityIntent)
                    } else {
                        showError()
                    }
                }
            } else {
                Toast.makeText(this, "Uno de los campos esta vacio", Toast.LENGTH_LONG).show()
            }
        }

        buttonSalir.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val prefs:SharedPreferences.Editor=getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()
            sesion()
        }

        buttonAtras.setOnClickListener {
            finish()
        }

    }

    private fun sesion() {
        val prefs:SharedPreferences=getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email= prefs.getString("email", null)

        if (email == null) {
            buttonSalir.isEnabled = false
            buttonIniciar.isEnabled = true
            buttonRegistrar.isEnabled = true
        } else {
            buttonSalir.isEnabled = true
            buttonIniciar.isEnabled = false
            buttonRegistrar.isEnabled = false
        }
    }

    private fun persistencia() {
        val prefs:SharedPreferences.Editor=getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", editTextEmail.text.toString())
        prefs.apply()
    }

    private fun showOK() {
        val builder = AlertDialog.Builder(this);
        builder.setTitle("Ha funcionado")
        builder.setMessage("Todo salió bien y se conecto correctamente")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog= builder.create()
        dialog.show()
    }

    private fun showError() {
        val builder = AlertDialog.Builder(this);
        builder.setTitle("No ha funcionado")
        builder.setMessage("Algo salió mal y no se conecto")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog= builder.create()
        dialog.show()
    }
}