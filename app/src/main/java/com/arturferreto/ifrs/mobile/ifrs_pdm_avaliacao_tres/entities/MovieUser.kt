package com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_user")
data class MovieUser(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val movieId: Int,
    val watched: Boolean,
    val liked: Boolean,
    val rated: Int
)
