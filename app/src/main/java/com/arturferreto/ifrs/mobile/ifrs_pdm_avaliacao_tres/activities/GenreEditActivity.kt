package com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.R
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.dao.GenreDao
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.database.AppDatabase
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities.Genre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GenreEditActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var genreDao: GenreDao
    private lateinit var buttonUpdateGenre: Button
    private lateinit var buttonDeleteGenre: Button
    private lateinit var editTextNameGenre: EditText
    private var genreId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre_edit)

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-db"
        ).build()

        genreDao = database.genreDao()

        genreId = intent.getIntExtra("ENTITY_ID", 0)

        buttonDeleteGenre = findViewById(R.id.buttonDeleteGenre)
        buttonUpdateGenre = findViewById(R.id.buttonUpdateGenre)
        editTextNameGenre = findViewById(R.id.editTextNameGenre)

        lifecycleScope.launch {
            val genre = genreDao.getOne(genreId)
            genre?.let {
                editTextNameGenre.setText(genre.name)
            } ?: run {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@GenreEditActivity,
                        "Gênero não encontrado.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                finish()
            }
        }

        buttonUpdateGenre.setOnClickListener {
            val name = editTextNameGenre.text.toString().trim()
            if (name.isNotEmpty()) {
                lifecycleScope.launch {
                    val genre = Genre(id = genreId, name = name)
                    genreDao.update(genre)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@GenreEditActivity,
                            "Gênero atualizado com sucesso.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    finish()
                }
            } else {
                Toast.makeText(
                    this@GenreEditActivity,
                    "Preencha o nome do gênero.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        buttonDeleteGenre.setOnClickListener {
            lifecycleScope.launch {
                val genre = genreDao.getOne(genreId)
                val notification: String?

                if (genre != null) {
                    genreDao.delete(genre)
                    notification = "Fornecedor excluído com sucesso."
                } else {
                    notification = "Fornecedor não encontrado."
                }

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@GenreEditActivity, notification, Toast.LENGTH_SHORT)
                        .show()
                }
                finish()
            }
        }
    }

}