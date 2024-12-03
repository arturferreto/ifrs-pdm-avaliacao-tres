package com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre_movie")
data class GenreMovie(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val genreId: Int,
    val movieId: Int
)
