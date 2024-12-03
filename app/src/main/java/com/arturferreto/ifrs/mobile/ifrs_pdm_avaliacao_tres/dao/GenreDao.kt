package com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities.Genre

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(genre: Genre): Long

    @Query("SELECT * FROM genres")
    suspend fun getAll(): List<Genre>

    @Delete
    suspend fun delete(genre: Genre)

}