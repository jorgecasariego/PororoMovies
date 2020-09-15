package me.jorgecasariego.pororopeliculas.repository.movies

import androidx.lifecycle.LiveData
import me.jorgecasariego.pororopeliculas.model.Movie
import me.jorgecasariego.pororopeliculas.network.response.MovieResponse

interface IMovieRepository {
    suspend fun getMoviesFromServer(
            category: String,
            apiKey: String,
            language: String,
            page: Int
    ): MovieResponse

    suspend fun insertMovies(movies: Array<Movie>)

    suspend fun getMovies(
            category: String,
            language: String,
            page: Int
    ): LiveData<List<Movie>?>
}