package com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class Genre(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)
