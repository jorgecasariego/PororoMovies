package me.jorgecasariego.pororopeliculas.repository.movies.datasource

import me.jorgecasariego.pororopeliculas.network.MovieDbApi
import me.jorgecasariego.pororopeliculas.network.response.MovieResponse
import retrofit2.http.Path
import retrofit2.http.Query

class MovieRemoteDatasource(private val movieDbApi: MovieDbApi) : IMovieRemoteDatasource {

    override suspend fun getMoviesFromServer(
            category: String,
            apiKey: String,
            language: String,
            page: Int
    ): MovieResponse {
        return movieDbApi.getMovies(category, apiKey, language, page)
    }
}