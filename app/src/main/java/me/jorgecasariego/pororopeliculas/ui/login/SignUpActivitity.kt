package me.jorgecasariego.pororopeliculas.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_login.*
import me.jorgecasariego.pororopeliculas.R
import me.jorgecasariego.pororopeliculas.databinding.ActivitySignUpActivitityBinding
import me.jorgecasariego.pororopeliculas.model.UserInformation
import me.jorgecasariego.pororopeliculas.ui.MainActivity
import org.koin.android.ext.android.inject

class SignUpActivitity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpActivitityBinding
    private val loginViewModel: LoginViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpActivitityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListener()
    }

    private fun setupListener() {
        binding.buttonSignUp.setOnClickListener { signUpUser() }

        binding.textViewLogin.setOnClickListener { onBackPressed()}
    }

    private fun signUpUser() {

        if (binding.editTextPassword.text.isEmpty() &&
                (binding.editTextPassword.text.toString() != binding.editTextConfirmPassword.text.toString())) {
            Toast.makeText(this, "Passwords are not equal", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.editTextName.text.isEmpty() || binding.editTextEmail.text.isEmpty()) {
            Toast.makeText(this, "All fields need to be complete", Toast.LENGTH_LONG).show()
            return
        }

        val newUser = UserInformation(
                name = binding.editTextName.text.toString(),
                email = binding.editTextEmail.text.toString(),
                password = binding.editTextPassword.text.toString())

        loginViewModel.setUserInfo(newUser)
        showMovies()
    }

    private fun showMovies() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}