package me.jorgecasariego.pororopeliculas.ui.viewholders

import android.content.Context
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.movie_item.view.*
import me.jorgecasariego.pororopeliculas.ui.PeliculaInterface
import me.jorgecasariego.pororopeliculas.R
import me.jorgecasariego.pororopeliculas.model.Model

class MovieItem(
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
