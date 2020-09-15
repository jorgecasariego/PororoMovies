package me.jorgecasariego.pororopeliculas.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_favorite_movies.*
import me.jorgecasariego.pororopeliculas.R
import me.jorgecasariego.pororopeliculas.database.MovieDatabase
import me.jorgecasariego.pororopeliculas.model.Movie
import me.jorgecasariego.pororopeliculas.ui.movie.MovieItem

class FavoriteMoviesActivity : AppCompatActivity() {

    lateinit var peliculasDatabase: MovieDatabase
    var peliculas: List<Movie> = arrayListOf()
    var peliculasFiltradas: List<Movie>  = arrayListOf()

//    override fun OnFavoritoClicked(pelicula: Movie) {
//        val resultado = peliculasDatabase.insertarPeliculaFavorita(pelicula)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_movies)

//        mostrarPeliculasFavoritas()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_favoritos, menu)
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        when(item?.itemId) {
//            R.id.favoritos -> {
//                peliculasFiltradas = peliculas.filter {
//                    it.es_favorito == true
//                }
//            }
//            R.id.todos -> {
//                peliculasFiltradas = peliculas
//            }
//        }
//        crearVista()
//
//        return super.onOptionsItemSelected(item)
//    }
//
//    private fun mostrarPeliculasFavoritas() {
//        peliculas = peliculasDatabase.getPeliculasFavoritas()
//        peliculasFiltradas = peliculas
//
//        crearVista()
//    }
//
//    private fun crearVista() {
//        val adapter = GroupAdapter<ViewHolder>()
//        peliculasFiltradas.forEach {
//            adapter.add(MovieItem(it, this, this))
//        }
//        mis_favoritas.adapter = adapter
//    }
}
