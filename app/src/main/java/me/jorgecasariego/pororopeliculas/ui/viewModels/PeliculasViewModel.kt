package me.jorgecasariego.pororopeliculas.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.jorgecasariego.pororopeliculas.PeliculasActivity
import me.jorgecasariego.pororopeliculas.model.Model
import me.jorgecasariego.pororopeliculas.network.MovieDbApi

class PeliculasViewModel: ViewModel() {

    private  val movieDbApi by lazy {
        MovieDbApi.create()
    }

    val movies: LiveData<List<Model.Movie>?> = MutableLiveData()

    init {
        viewModelScope.launch {
            movies as MutableLiveData
            movies.value = getMovies()
        }
    }

    private suspend fun getMovies(): List<Model.Movie>? {
        return withContext(Dispatchers.IO) {
            movieDbApi.getMovies(PeliculasActivity.CATEGORY, PeliculasActivity.API_KEY, PeliculasActivity.LANGUAGE, PeliculasActivity.PAGE).body()?.results
        }
    }
}