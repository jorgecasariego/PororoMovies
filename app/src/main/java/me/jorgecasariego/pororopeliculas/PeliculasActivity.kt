package me.jorgecasariego.pororopeliculas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_peliculas.*
import kotlinx.android.synthetic.main.movie_item.view.*
import me.jorgecasariego.pororopeliculas.database.PeliculasDatabase
import me.jorgecasariego.pororopeliculas.model.Model
import me.jorgecasariego.pororopeliculas.network.MovieDbApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PeliculasActivity : AppCompatActivity(), PeliculaInterface {
    override fun OnFavoritoClicked(pelicula: Model.Movie) {
        val resultado = peliculasDatabase.insertarPeliculaFavorita(pelicula)

        if (resultado) {
            Toast.makeText(this, "Pelicula favorita guardada exitosamente", Toast.LENGTH_LONG).show()
        }
    }

    private  val movieDbApi by lazy {
        MovieDbApi.create()
    }

    lateinit var peliculasDatabase: PeliculasDatabase

    companion object {
        val PAGE = 1
        val LANGUAGE = "en-US"
        val API_KEY = "7ac3ca0156061195fead747e09bd53a1"
        val CATEGORY = "popular"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_peliculas)

        peliculasDatabase = PeliculasDatabase(this)

        getPeliculas()
    }

    private fun getPeliculas() {
        val call = movieDbApi.getMovies(CATEGORY, API_KEY, LANGUAGE, PAGE)

        call.enqueue(object : Callback<Model.MovieResults> {
            override fun onFailure(call: Call<Model.MovieResults>?, t: Throwable?) {
                Toast.makeText(this@PeliculasActivity,
                        "Fallo al obtener peliculas",
                        Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Model.MovieResults>?,
                                    response: Response<Model.MovieResults>?) {

                if (response != null) {
                    val peliculas: Model.MovieResults? = response.body()

                    Log.d("TEST", "Cantidad de peliculas es : ${peliculas?.total_results}");

                    val adapter = GroupAdapter<ViewHolder>()

                    peliculas?.results?.forEach {
                        adapter.add(
                                MoviewItem(
                                        it,
                                        this@PeliculasActivity,
                                        this@PeliculasActivity))
                    }

                    listado_peliculas.adapter = adapter
                }

            }

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
}

interface PeliculaInterface {
    fun OnFavoritoClicked(pelicula: Model.Movie)
}

class MoviewItem(
        val pelicula: Model.Movie,
        val context: Context,
        val listener: PeliculaInterface): Item<ViewHolder>() {

    companion object {
        val imageUrlBase = "https://image.tmdb.org/t/p/w500"
    }
    override fun getLayout(): Int {
        return R.layout.movie_item
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.nombre_pelicula.text = pelicula.title
        viewHolder.itemView.sinopsis_pelicula.text = pelicula.overview
        viewHolder.itemView.estreno_pelicula.text = pelicula.release_date

        Picasso.get().load(imageUrlBase + pelicula.poster_path)
                .into(viewHolder.itemView.imagen_pelicula)

        if (pelicula.es_favorito) {
            viewHolder.itemView.favorito.setImageDrawable(
                    ContextCompat.getDrawable(context, R.drawable.ic_favorite_red))
        } else {
            viewHolder.itemView.favorito.setImageDrawable(
                    ContextCompat.getDrawable(context, R.drawable.ic_favorite_white))
        }

        viewHolder.itemView.favorito.setOnClickListener {
            if (pelicula.es_favorito) {
                viewHolder.itemView.favorito.setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.ic_favorite_white))
            } else {
                viewHolder.itemView.favorito.setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.ic_favorite_red))
            }

            pelicula.es_favorito = !pelicula.es_favorito

            listener.OnFavoritoClicked(pelicula)
        }
    }

}















