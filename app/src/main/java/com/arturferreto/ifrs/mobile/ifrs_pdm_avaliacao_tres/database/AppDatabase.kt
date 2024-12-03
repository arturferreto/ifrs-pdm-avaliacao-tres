package com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.dao.GenreDao
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.dao.MovieDao
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.dao.MovieUserDao
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.dao.UserDao
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities.Genre
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities.GenreMovie
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities.Movie
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities.MovieUser
import com.arturferreto.ifrs.mobile.ifrs_pdm_avaliacao_tres.entities.User

@Database(
    entities = [User::class, Movie::class, MovieUser::class, Genre::class, GenreMovie::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
    abstract fun movieUserDao(): MovieUserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}