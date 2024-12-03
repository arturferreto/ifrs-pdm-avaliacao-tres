package com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.R
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.database.AppDatabase
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities.User
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var backToLoginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        usernameEditText = findViewById(R.id.usernameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        nameEditText = findViewById(R.id.nameEditText)
        registerButton = findViewById(R.id.registerButton)
        backToLoginButton = findViewById(R.id.backToLoginButton)

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val name = nameEditText.text.toString()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {
                registerUser(username, email, password, name)
            } else {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }

        backToLoginButton.setOnClickListener {
            finish()
        }
    }

    private fun registerUser(username: String, email: String, password: String, name: String) {
        val db = AppDatabase.getDatabase(this)
        val userDao = db.userDao()

        lifecycleScope.launch {
            val newUser = User(
                username = username,
                email = email,
                password = password,
                name = name
            )
            userDao.insert(newUser)

            Toast.makeText(this@RegisterActivity, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
            finish() // Voltar para a tela de login
        }
    }
}