package me.jorgecasariego.pororopeliculas.network.response

import com.google.gson.annotations.SerializedName
import me.jorgecasariego.pororopeliculas.model.Movie

data class MovieResponse (
        @SerializedName("page")
        val page: Int,
        @SerializedName("total_results")
        val totalResults: Int,
        @SerializedName("total_pages")
        val totalPages: Int,
        @SerializedName("results")
        val results: ArrayList<Movie>
)

