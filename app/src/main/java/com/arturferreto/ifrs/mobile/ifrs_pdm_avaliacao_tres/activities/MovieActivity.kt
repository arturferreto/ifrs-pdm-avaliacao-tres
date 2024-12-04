package com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.R
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.dao.MovieDao
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.database.AppDatabase
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities.Movie
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var movieDao: MovieDao
    private lateinit var editTextTitleMovie: EditText
    private lateinit var editTextDescriptionMovie: EditText
    private lateinit var editTextYearMovie: EditText
    private lateinit var buttonAddMovie: Button
    private lateinit var listViewMovies: ListView

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-db"
        ).build()

        movieDao = database.movieDao()

        listViewMovies = findViewById(R.id.listViewMovies)
        buttonAddMovie = findViewById(R.id.buttonAddMovie)
        editTextTitleMovie = findViewById(R.id.editTextTitleMovie)
        editTextDescriptionMovie = findViewById(R.id.editTextDescriptionMovie)
        editTextYearMovie = findViewById(R.id.editTextYearMovie)

        buttonAddMovie.setOnClickListener {
            val title = editTextTitleMovie.text.toString().trim()
            val description = editTextDescriptionMovie.text.toString().trim()
            val year = editTextYearMovie.text.toString().trim()

            if (title.isNotEmpty() && description.isNotEmpty() && year.isNotEmpty()) {
                GlobalScope.launch(Dispatchers.IO) {
                    val movie = Movie(title = title, description = description, year = year.toInt())
                    movieDao.insert(movie)
                    runOnUiThread { loadMovies() }
                }
            }
        }

        listViewMovies.setOnItemClickListener { _, _, position, _ ->
            GlobalScope.launch(Dispatchers.IO) {
                val selectedMovie = movieDao.getAll()[position]
                val i = Intent(this@MovieActivity, MovieEditActivity::class.java)
                i.putExtra("ENTITY_ID", selectedMovie.id)
                startActivity(i)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadMovies() {
        GlobalScope.launch(Dispatchers.IO) {
            val movies = movieDao.getAll()
            runOnUiThread {
                val adapter = ArrayAdapter(
                    this@MovieActivity,
                    android.R.layout.simple_list_item_1,
                    movies.map { it.title }
                )
                listViewMovies.adapter = adapter
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadMovies()
    }

}