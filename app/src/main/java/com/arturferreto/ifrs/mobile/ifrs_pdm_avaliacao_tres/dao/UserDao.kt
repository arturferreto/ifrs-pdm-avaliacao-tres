package com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities.User
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.relations.UserWatchedMovies

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM users")
    suspend fun getAll(): List<User>

    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserWithMovies(userId: Int): UserWatchedMovies

    @Delete
    suspend fun delete(user: User)

}
