package com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val year: Int,
)
