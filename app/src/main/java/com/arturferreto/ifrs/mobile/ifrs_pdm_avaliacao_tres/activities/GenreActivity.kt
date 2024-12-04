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
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.dao.GenreDao
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.database.AppDatabase
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities.Genre
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GenreActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var genreDao: GenreDao
    private lateinit var listViewGenres: ListView
    private lateinit var buttonAddGenre: Button
    private lateinit var editTextNameGenre: EditText

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre)

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-db"
        ).build()

        genreDao = database.genreDao()

        listViewGenres = findViewById(R.id.listViewGenres)
        buttonAddGenre = findViewById(R.id.buttonAddGenre)
        editTextNameGenre = findViewById(R.id.editTextNameGenre)

        buttonAddGenre.setOnClickListener {
            val name = editTextNameGenre.text.toString().trim()
            if (name.isNotEmpty()) {
                GlobalScope.launch(Dispatchers.IO) {
                    val genre = Genre(name = name)
                    genreDao.insert(genre)
                    runOnUiThread { loadGenres() }
                }
            }
        }

        listViewGenres.setOnItemClickListener { _, _, position, _ ->
            GlobalScope.launch(Dispatchers.IO) {
                val selectedGenre = genreDao.getAll()[position]
                val i = Intent(this@GenreActivity, GenreEditActivity::class.java)
                i.putExtra("ENTITY_ID", selectedGenre.id)
                startActivity(i)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadGenres() {
        GlobalScope.launch(Dispatchers.IO) {
            val genres = genreDao.getAll()
            runOnUiThread {
                val adapter = ArrayAdapter(
                    this@GenreActivity,
                    android.R.layout.simple_list_item_1,
                    genres.map { it.name }
                )
                listViewGenres.adapter = adapter
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadGenres()
    }

}