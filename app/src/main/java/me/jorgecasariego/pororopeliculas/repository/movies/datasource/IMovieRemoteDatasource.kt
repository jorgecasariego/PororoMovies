package me.jorgecasariego.pororopeliculas.repository.movies.datasource

import me.jorgecasariego.pororopeliculas.network.response.MovieResponse

interface IMovieRemoteDatasource {
    suspend fun getMoviesFromServer(
            category: String,
            apiKey: String,
            language: String,
            page: Int): MovieResponse
}