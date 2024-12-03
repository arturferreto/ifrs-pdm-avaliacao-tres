package com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities.MovieUser
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.relations.MovieWithDetails

@Dao
interface MovieUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieUser: MovieUser): Long

    @Transaction
    @Query("SELECT * FROM movie_user WHERE userId = :userId")
    suspend fun getMoviesByUser(userId: Int): List<MovieWithDetails>

    @Update
    suspend fun update(movieUser: MovieUser)

    @Delete
    suspend fun delete(movieUser: MovieUser)

}