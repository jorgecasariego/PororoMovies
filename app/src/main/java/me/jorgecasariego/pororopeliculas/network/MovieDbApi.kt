package me.jorgecasariego.pororopeliculas.network

import me.jorgecasariego.pororopeliculas.network.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDbApi {

    @GET("movie/{category}")
    suspend fun getMovies(
            @Path("category") category: String,
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int
    ): MovieResponse
}