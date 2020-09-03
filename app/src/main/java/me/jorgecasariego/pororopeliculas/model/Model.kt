package me.jorgecasariego.pororopeliculas.model

object Model {
    data class MovieResults (
        val page: Int,
        val total_results: Int,
        val total_pages: Int,
        val results: ArrayList<Movie>
        )

    data class Movie(
            val id: Int,
            val title: String,
            val poster_path: String,
            val original_language: String,
            val original_title: String,
            val overview: String,
            val release_date: String,
            var es_favorito: Boolean
    )
}