package com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities.Movie
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities.MovieUser

data class MovieWithDetails(
    @Embedded val movieUser: MovieUser,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "id"
    )
    val movie: Movie
)
