package me.jorgecasariego.pororopeliculas.repository.movies.datasource

import androidx.lifecycle.LiveData
import me.jorgecasariego.pororopeliculas.database.MovieDatabaseConstants
import me.jorgecasariego.pororopeliculas.model.Movie

interface IMovieLocalDatasource {
    suspend fun insertMovies(movies: Array<Movie>)

    suspend fun getMovies(category: String): LiveData<List<Movie>?>
}