package me.jorgecasariego.pororopeliculas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_peliculas.*
import kotlinx.android.synthetic.main.movie_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.jorgecasariego.pororopeliculas.database.PeliculasDatabase
import me.jorgecasariego.pororopeliculas.model.Model
import me.jorgecasariego.pororopeliculas.network.MovieDbApi
import me.jorgecasariego.pororopeliculas.ui.viewModels.PeliculasViewModel
import me.jorgecasariego.pororopeliculas.ui.viewholders.MovieItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PeliculasActivity : AppCompatActivity(), PeliculaInterface {

    lateinit var peliculasDatabase: PeliculasDatabase
    val adapter = GroupAdapter<ViewHolder>()
    private lateinit var viewModel: PeliculasViewModel

    companion object {
        val PAGE = 1
        val LANGUAGE = "en-US"
        val API_KEY = "7ac3ca0156061195fead747e09bd53a1"
        val CATEGORY = "popular"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_peliculas)

        setupViewModel()

        peliculasDatabase = PeliculasDatabase( this)

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(PeliculasViewModel::class.java)
        viewModel.movies.observe(this, Observer { movies ->
            movies?.forEach {
                adapter.add(
                        MovieItem(
                                it,
                                this@PeliculasActivity,
                                this@PeliculasActivity))
            }

            listado_peliculas.adapter = adapter
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.ver_favoritos) {
            startActivity(Intent(this, MisFavoritosActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }

    override fun OnFavoritoClicked(pelicula: Model.Movie) {
        val resultado = peliculasDatabase.insertarPeliculaFavorita(pelicula)

        if (resultado) {
            Toast.makeText(this, "Pelicula favorita guardada exitosamente", Toast.LENGTH_LONG).show()
        }
    }
}

interface PeliculaInterface {
    fun OnFavoritoClicked(pelicula: Model.Movie)
}















