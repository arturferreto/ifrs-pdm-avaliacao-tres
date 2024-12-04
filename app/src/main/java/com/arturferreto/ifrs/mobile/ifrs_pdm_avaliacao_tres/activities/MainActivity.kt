package com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val buttonGenre = findViewById<Button>(R.id.buttonGoToGenre)
        buttonGenre.setOnClickListener {
            val intent = Intent(this, GenreActivity::class.java)
            startActivity(intent)
        }
    }
}