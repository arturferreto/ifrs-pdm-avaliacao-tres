package com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities.Movie
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.relations.MovieWithGenres

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie): Long

    @Query("SELECT * FROM movies")
    suspend fun getAll(): List<Movie>

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getOne(id: Int): Movie?

    @Transaction
    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovieWithGenres(movieId: Int): MovieWithGenres

    @Update
    suspend fun update(movie: Movie): Int

    @Delete
    suspend fun delete(movie: Movie)

}
