package me.jorgecasariego.pororopeliculas.network

import me.jorgecasariego.pororopeliculas.model.Model
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDbApi {

    @GET("movie/{category}")
    fun getMovies(
            @Path("category") category: String,
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int
    ): Call<Model.MovieResults>


    companion object {
        val BASE_URL = "https://api.themoviedb.org/3/"

        fun create(): MovieDbApi {
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(MovieDbApi::class.java)
        }
    }
}