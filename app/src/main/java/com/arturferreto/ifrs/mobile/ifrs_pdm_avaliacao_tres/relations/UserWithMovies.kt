package com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities.MovieUser
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities.User

data class UserWithMovies(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val movies: List<MovieUser>
)
