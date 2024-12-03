package com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre_movie", primaryKeys = ["genreId", "movieId"])
data class GenreMovie(
    val genreId: Int,
    val movieId: Int
)
