package me.jorgecasariego.pororopeliculas.repository.movies.datasource

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Database
import me.jorgecasariego.pororopeliculas.database.MovieDatabase
import me.jorgecasariego.pororopeliculas.model.Movie
import java.lang.RuntimeException

class MovieLocalDatasource(private val applicationContext: Context) : IMovieLocalDatasource {

    private fun movieDatabase(): MovieDatabase? =
            MovieDatabase.getInstance(applicationContext)
                    ?: throw RuntimeException("No database selected for current user")

    override suspend fun insertMovies(movies: Array<Movie>) {
        movieDatabase()!!.movieDao().insertAll(movies)
    }

    override suspend fun getMovies(category: String): LiveData<List<Movie>?> {
        return movieDatabase()!!.movieDao().getMoviesByCategory()
    }
}