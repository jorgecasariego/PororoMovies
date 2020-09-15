package me.jorgecasariego.pororopeliculas.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import me.jorgecasariego.pororopeliculas.database.MovieDatabaseConstants
import me.jorgecasariego.pororopeliculas.model.Movie

@Dao
abstract class MovieDao: BaseDao<Movie> {

    @Query("SELECT * FROM ${MovieDatabaseConstants.Movie.TABLE_NAME}")
    abstract fun getMoviesByCategory(): LiveData<List<Movie>?>
}