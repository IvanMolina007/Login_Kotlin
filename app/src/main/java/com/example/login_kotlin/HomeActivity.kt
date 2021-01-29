package com.example.login_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val GOOGLE_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


            buttonEmail.setOnClickListener {
                val nextActivityIntent = Intent(this, Email_password::class.java)
                startActivity(nextActivityIntent)
            }

            buttonGoogle.setOnClickListener {
                val googleConf =
                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(
                            "23569719178-577tld28a8009nbqommb240vf30hua01.apps.googleusercontent.com"
                        )
                        .requestEmail()
                        .build()
                val googleClient = GoogleSignIn.getClient(this, googleConf)
                googleClient.signOut()

                startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
            }

            buttonAnonimo.setOnClickListener {
                FirebaseAuth.getInstance().signInAnonymously()
                val nextActivityIntent = Intent(this, JuegoPPTActivity::class.java).apply {
                    putExtra("servicio", 2)
                }
                startActivity(nextActivityIntent)
            }
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == GOOGLE_SIGN_IN) {

                val task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)

                try {
                    val account : GoogleSignInAccount? = task.getResult(ApiException::class.java)

                    if (account != null) {

                        val credential: AuthCredential = GoogleAuthProvider.getCredential(
                            account.idToken,
                            null
                        )

                        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {

                            if (it.isSuccessful) {
                                showOK()
                                val nextActivityIntent = Intent(this, JuegoPPTActivity::class.java).apply {
                                    putExtra("email", account.email)
                                    putExtra("servicio", 1)
                                }
                                startActivity(nextActivityIntent)
                            } else {
                                showError()
                            }

                        }
                    }
                } catch (e: ApiException) {
                    showError()
                }
            }
        }

        private fun showOK() {
            val builder = AlertDialog.Builder(this);
            builder.setTitle("Ha funcionado")
            builder.setMessage("Todo salió bien y se conecto correctamente")
            builder.setPositiveButton("Aceptar", null)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        private fun showError() {
            val builder = AlertDialog.Builder(this);
            builder.setTitle("No ha funcionado")
            builder.setMessage("Algo salió mal y no se conecto")
            builder.setPositiveButton("Aceptar", null)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
