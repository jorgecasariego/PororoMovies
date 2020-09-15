package me.jorgecasariego.pororopeliculas.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import me.jorgecasariego.pororopeliculas.R
import me.jorgecasariego.pororopeliculas.model.UserInformation
import me.jorgecasariego.pororopeliculas.ui.MainActivity
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {
    val keyUsuarioSP = "keyUsuarioSP"

    lateinit var sharedPreferences: SharedPreferences
    private val loginViewModel: LoginViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences = getSharedPreferences(keyUsuarioSP, Context.MODE_PRIVATE)

        // Control de click en los botones de ingresar y registrarse
        ingresar_usuario.setOnClickListener { iniciarSesion() }
        registrar_usuario.setOnClickListener { registrarUsuario() }
    }

    private fun registrarUsuario() {
        val nombreUsuario = nombre.text.toString()
        val passwordUsuario = password.text.toString()

        if (nombreUsuario.isEmpty() || passwordUsuario.isEmpty()) {
            Toast.makeText(this, "El usuario y/o password no pueden quedar vacios",
                    Toast.LENGTH_LONG).show()
        } else {
            // Guardar los datos del usuario en nuestras SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("NOMBRE", nombreUsuario)
            editor.putString("PASSWORD", passwordUsuario)
            editor.apply()

            setUserInfo(nombreUsuario, passwordUsuario)

            gotoPeliculasActivity()
        }
    }

    private fun setUserInfo(nombreUsuario: String, passwordUsuario: String) {
        val userInformation = UserInformation(
                username = nombreUsuario,
                password = passwordUsuario
        )

        loginViewModel.setUserInfo(userInformation)
    }

    private fun iniciarSesion() {
        val nombreUsuario = nombre.text.toString()
        val passwordUsuario = password.text.toString()

        if (nombreUsuario.isEmpty() || passwordUsuario.isEmpty()) {
            Toast.makeText(this,
                    "El usuario y/o password no pueden quedar vacios",
                    Toast.LENGTH_LONG).show()
        } else {
            val nombreGuardado = sharedPreferences.getString("NOMBRE", "")
            val passwordGuardado = sharedPreferences.getString("PASSWORD", "")

            if (nombreUsuario.equals(nombreGuardado) && passwordUsuario.equals(passwordGuardado)) {
                setUserInfo(nombreUsuario, passwordUsuario)
                gotoPeliculasActivity()
            } else {
                Toast.makeText(this,
                        "El usuario y/o la contraseña son incorrectas",
                        Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun gotoPeliculasActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }



}














