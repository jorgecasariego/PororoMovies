package me.jorgecasariego.pororopeliculas.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_mis_favoritos.*
import me.jorgecasariego.pororopeliculas.R
import me.jorgecasariego.pororopeliculas.database.PeliculasDatabase
import me.jorgecasariego.pororopeliculas.model.Model
import me.jorgecasariego.pororopeliculas.ui.viewholders.MovieItem

class MisFavoritosActivity : AppCompatActivity(), PeliculaInterface {

    lateinit var peliculasDatabase: PeliculasDatabase
    var peliculas: List<Model.Movie> = arrayListOf()
    var peliculasFiltradas: List<Model.Movie>  = arrayListOf()

    override fun OnFavoritoClicked(pelicula: Model.Movie) {
        val resultado = peliculasDatabase.insertarPeliculaFavorita(pelicula)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_favoritos)

        peliculasDatabase = PeliculasDatabase(this)
        mostrarPeliculasFavoritas()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_favoritos, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.favoritos -> {
                peliculasFiltradas = peliculas.filter {
                    it.es_favorito == true
                }
            }
            R.id.todos -> {
                peliculasFiltradas = peliculas
            }
        }
        crearVista()

        return super.onOptionsItemSelected(item)
    }

    private fun mostrarPeliculasFavoritas() {
        peliculas = peliculasDatabase.getPeliculasFavoritas()
        peliculasFiltradas = peliculas

        crearVista()
    }

    private fun crearVista() {
        val adapter = GroupAdapter<ViewHolder>()
        peliculasFiltradas.forEach {
            adapter.add(MovieItem(it, this, this))
        }
        mis_favoritas.adapter = adapter
    }
}
