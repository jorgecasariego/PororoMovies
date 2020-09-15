package me.jorgecasariego.pororopeliculas.ui.movie

import androidx.lifecycle.Observer
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_movies.*
import me.jorgecasariego.pororopeliculas.R
import me.jorgecasariego.pororopeliculas.databinding.FragmentMoviesBinding
import me.jorgecasariego.pororopeliculas.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MoviesFragment : BaseFragment<FragmentMoviesBinding>() {

    val adapter = GroupAdapter<ViewHolder>()
    private val viewModel: MoviesViewModel by sharedViewModel()
    private lateinit var viewBinding: FragmentMoviesBinding

    override fun layout() = R.layout.fragment_movies

    override fun onUICreated(viewBinding: FragmentMoviesBinding) {
        this.viewBinding = viewBinding

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
        viewModel.movies.observe(this, Observer { movies ->
            movies?.forEach {
                adapter.add(
                        MovieItem(
                                it,
                                requireContext()))
            }

            listado_peliculas.adapter = adapter
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















