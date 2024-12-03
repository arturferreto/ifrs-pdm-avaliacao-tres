package com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities

import androidx.room.Entity

@Entity(tableName = "movie_user", primaryKeys = ["userId", "movieId"])
data class MovieUser(
    val userId: Int,
    val movieId: Int,
    val watched: Boolean,
    val liked: Boolean,
    val rated: Int
)
