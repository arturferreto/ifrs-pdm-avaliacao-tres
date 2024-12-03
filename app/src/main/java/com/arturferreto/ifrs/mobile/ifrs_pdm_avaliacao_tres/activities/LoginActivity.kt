package com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.R
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.database.AppDatabase
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)

        sharedPreferences = getSharedPreferences("ifrs_pdm_avaliacao_tres", MODE_PRIVATE)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                authenticateUser(username, password)
            } else {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun authenticateUser(username: String, password: String) {
        val db = AppDatabase.getDatabase(this)
        val userDao = db.userDao()

        lifecycleScope.launch {
            val user = userDao.getAll().find { it.username == username && it.password == password }
            if (user != null) {
                saveSession(user.id)
                Toast.makeText(this@LoginActivity, "Bem-vindo, ${user.name}!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@LoginActivity, "Usuário ou senha inválidos!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveSession(userId: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("USER_ID", userId)
        editor.apply()
    }
}