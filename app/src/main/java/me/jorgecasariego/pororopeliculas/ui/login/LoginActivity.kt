package me.jorgecasariego.pororopeliculas.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import me.jorgecasariego.pororopeliculas.databinding.ActivityLoginBinding
import me.jorgecasariego.pororopeliculas.ui.MainActivity
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by inject()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Control de click en los botones de ingresar y registrarse
        button_signIn.setOnClickListener {
            iniciarSesion()
        }

        button_signUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivitity::class.java))
        }
    }

    private fun iniciarSesion() {
        if (binding.editTextEmail.text.isEmpty() || binding.editTextPassword.text.isEmpty()) {
            Toast.makeText(this, "Email/Password can't be empty", Toast.LENGTH_LONG).show()
            return
        }

        val userInformation = loginViewModel.getUserInfoByEmail(binding.editTextEmail.text.toString())

        if (userInformation == null) {
            Toast.makeText(this, "User not registered in the app. Press SignUp to Register", Toast.LENGTH_LONG).show()
            return
        }

        if (userInformation.password == binding.editTextPassword.text.toString()) {
            gotoMoviesActivity()
        } else {
            Toast.makeText(this, "Incorrect Password. Press forgot Password to reset your password.", Toast.LENGTH_LONG).show()
        }

    }


    private fun gotoMoviesActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }



}














