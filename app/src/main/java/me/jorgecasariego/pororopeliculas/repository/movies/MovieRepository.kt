package me.jorgecasariego.pororopeliculas.repository.movies

import androidx.lifecycle.LiveData
import me.jorgecasariego.pororopeliculas.model.Movie
import me.jorgecasariego.pororopeliculas.network.response.MovieResponse
import me.jorgecasariego.pororopeliculas.repository.movies.datasource.IMovieLocalDatasource
import me.jorgecasariego.pororopeliculas.repository.movies.datasource.IMovieRemoteDatasource
import me.jorgecasariego.pororopeliculas.repository.movies.datasource.MovieLocalDatasource
import me.jorgecasariego.pororopeliculas.repository.movies.datasource.MovieRemoteDatasource

class MovieRepository(
        private val movieLocalDatasource: IMovieLocalDatasource,
        private val movieRemoteDatasource: IMovieRemoteDatasource
) : IMovieRepository {

    override suspend fun getMoviesFromServer(
            category: String,
            apiKey: String,
            language: String,
            page: Int
    ): MovieResponse {
        return movieRemoteDatasource.getMoviesFromServer(category, apiKey, language, page)
    }

    override suspend fun insertMovies(movies: Array<Movie>) {
        movieLocalDatasource.insertMovies(movies)
    }

    override suspend fun getMovies(category: String, language: String, page: Int): LiveData<List<Movie>?> {
        return movieLocalDatasource.getMovies(category)
    }


}