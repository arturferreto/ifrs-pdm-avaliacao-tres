package com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities.Genre
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities.GenreMovie
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities.Movie

data class MovieWithGenres(
    @Embedded val movie: Movie,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = GenreMovie::class,
            parentColumn = "movieId",
            entityColumn = "genreId"
        )
    )
    val genres: List<Genre> = emptyList()
)