package me.jorgecasariego.pororopeliculas.ui.movie

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import me.jorgecasariego.pororopeliculas.R
import me.jorgecasariego.pororopeliculas.databinding.FragmentMoviesBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MoviesFragment : Fragment(R.layout.fragment_movies) {

    val adapter = GroupAdapter<ViewHolder>()
    private val viewModel: MoviesViewModel by sharedViewModel()
    private lateinit var viewBinding: FragmentMoviesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentMoviesBinding.bind(view)

        initUI()
    }


    private fun initUI() {
        setupObserver()
        getMovies()
    }

    private fun getMovies() {
        viewModel.getMovies()
    }

    private fun setupObserver() {
        viewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
            movies?.forEach {
                adapter.add(
                        MovieItem(
                                it,
                                requireContext()))
            }

            viewBinding.listadoPeliculas.adapter = adapter
        })
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater: MenuInflater = menuInflater
//        inflater.inflate(R.menu.menu_principal, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        if (item?.itemId == R.id.ver_favoritos) {
//            startActivity(Intent(this, FavoriteMoviesActivity::class.java))
//        }
//
//        return super.onOptionsItemSelected(item)
//    }

//    override fun OnFavoritoClicked(pelicula: Movie) {
//        val resultado = peliculasDatabase.insertarPeliculaFavorita(pelicula)
//
//        if (resultado) {
//            Toast.makeText(this, "Pelicula favorita guardada exitosamente", Toast.LENGTH_LONG).show()
//        }
//    }
}

//interface PeliculaInterface {
//    fun OnFavoritoClicked(pelicula: Model.Movie)
//}















