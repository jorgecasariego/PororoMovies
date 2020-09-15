package me.jorgecasariego.pororopeliculas.ui.movie

import android.content.Context
import android.provider.SyncStateContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.jorgecasariego.pororopeliculas.base.Constants
import me.jorgecasariego.pororopeliculas.model.Movie
import me.jorgecasariego.pororopeliculas.network.MovieDbApi
import me.jorgecasariego.pororopeliculas.repository.movies.IMovieRepository
import me.jorgecasariego.pororopeliculas.repository.movies.MovieRepository
import me.jorgecasariego.pororopeliculas.workers.controller.WorkerController

class MoviesViewModel(
        private val context: Context,
        private val movieRepository: IMovieRepository
): ViewModel() {

    var movies: LiveData<List<Movie>?> = MutableLiveData()


    init {
        viewModelScope.launch {
            movies as MutableLiveData
            movies =  movieRepository.getMovies(
                    category = Constants.CATEGORY,
                    page = Constants.PAGE,
                    language = Constants.LANGUAGE
            )
        }
    }


    fun getMovies() {
        WorkerController.startGetMovies(
                context,
                "user1",
                Constants.CATEGORY
        )
    }
}