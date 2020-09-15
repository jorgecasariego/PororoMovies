package me.jorgecasariego.pororopeliculas.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import me.jorgecasariego.pororopeliculas.database.MovieDatabaseConstants

@Entity(tableName = MovieDatabaseConstants.Movie.TABLE_NAME)
data class Movie(
        @PrimaryKey
        @ColumnInfo(name = MovieDatabaseConstants.Movie.ID_MOVIE)
        @SerializedName("id") val id: Int,

        @ColumnInfo(name = MovieDatabaseConstants.Movie.MOVIE_TITLE)
        @SerializedName("title") val title: String,

        @ColumnInfo(name = MovieDatabaseConstants.Movie.MOVIE_POSTER_PATH)
        @SerializedName("poster_path") val posterPath: String,

        @ColumnInfo(name = MovieDatabaseConstants.Movie.MOVIE_ORIGINAL_LANGUAGE)
        @SerializedName("original_language") val originalLanguage: String,

        @ColumnInfo(name = MovieDatabaseConstants.Movie.MOVIE_ORIGINAL_TITLE)
        @SerializedName("original_title") val originalTitle: String,

        @ColumnInfo(name = MovieDatabaseConstants.Movie.MOVIE_OVERVIEW)
        @SerializedName("overview") val overview: String,

        @ColumnInfo(name = MovieDatabaseConstants.Movie.MOVIE_RELEASE_DATE)
        @SerializedName("release_date") val releaseDate: String,

        @ColumnInfo(name = MovieDatabaseConstants.Movie.MOVIE_IS_FAVORITE) var isFavorite: Boolean
)