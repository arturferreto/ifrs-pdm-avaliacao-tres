package com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.R
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.dao.MovieDao
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.database.AppDatabase
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieEditActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var movieDao: MovieDao
    private lateinit var editTextTitleMovie: EditText
    private lateinit var editTextDescriptionMovie: EditText
    private lateinit var editTextYearMovie: EditText
    private lateinit var buttonUpdateMovie: Button
    private lateinit var buttonDeleteMovie: Button
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_edit)

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-db"
        ).build()

        movieDao = database.movieDao()

        movieId = intent.getIntExtra("ENTITY_ID", 0)

        buttonDeleteMovie = findViewById(R.id.buttonDeleteMovie)
        buttonUpdateMovie = findViewById(R.id.buttonUpdateMovie)
        editTextTitleMovie = findViewById(R.id.editTextTitleMovie)
        editTextDescriptionMovie = findViewById(R.id.editTextDescriptionMovie)
        editTextYearMovie = findViewById(R.id.editTextYearMovie)

        lifecycleScope.launch {
            val movie = movieDao.getOne(movieId)
            movie?.let {
                editTextTitleMovie.setText(movie.title)
                editTextDescriptionMovie.setText(movie.description)
                editTextYearMovie.setText(movie.year.toString())
            } ?: run {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@MovieEditActivity,
                        "Filme não encontrado.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                finish()
            }
        }

        buttonUpdateMovie.setOnClickListener {
            val title = editTextTitleMovie.text.toString().trim()
            val description = editTextDescriptionMovie.text.toString().trim()
            val year = editTextYearMovie.text.toString().toIntOrNull()

            if (title.isNotEmpty() && description.isNotEmpty() && year != null) {
                lifecycleScope.launch {
                    val movie = Movie(id = movieId, title = title, description = description, year = year)
                    movieDao.update(movie)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@MovieEditActivity,
                            "Filme atualizado com sucesso.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    finish()
                }
            } else {
                Toast.makeText(
                    this@MovieEditActivity,
                    "Preencha todos os campos corretamente.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        buttonDeleteMovie.setOnClickListener {
            lifecycleScope.launch {
                val movie = movieDao.getOne(movieId)
                val notification: String?

                if (movie != null) {
                    movieDao.delete(movie)
                    notification = "Filme excluído com sucesso."
                } else {
                    notification = "Filme não encontrado."
                }

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MovieEditActivity, notification, Toast.LENGTH_SHORT)
                        .show()
                }
                finish()
            }
        }
    }

}