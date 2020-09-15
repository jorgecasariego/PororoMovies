package me.jorgecasariego.pororopeliculas.injection

import me.jorgecasariego.pororopeliculas.base.CoroutineContextProvider
import me.jorgecasariego.pororopeliculas.network.RetrofitConfig.provideMoviewDbApi
import me.jorgecasariego.pororopeliculas.network.RetrofitConfig.provideOkHttpClient
import me.jorgecasariego.pororopeliculas.network.RetrofitConfig.provideRetrofit
import me.jorgecasariego.pororopeliculas.repository.config.ConfigRepository
import me.jorgecasariego.pororopeliculas.repository.config.IConfigRepository
import me.jorgecasariego.pororopeliculas.repository.config.datasource.ILocalConfigDataSource
import me.jorgecasariego.pororopeliculas.repository.config.datasource.LocalConfigDataSource
import me.jorgecasariego.pororopeliculas.repository.movies.IMovieRepository
import me.jorgecasariego.pororopeliculas.repository.movies.MovieRepository
import me.jorgecasariego.pororopeliculas.repository.movies.datasource.IMovieLocalDatasource
import me.jorgecasariego.pororopeliculas.repository.movies.datasource.IMovieRemoteDatasource
import me.jorgecasariego.pororopeliculas.repository.movies.datasource.MovieLocalDatasource
import me.jorgecasariego.pororopeliculas.repository.movies.datasource.MovieRemoteDatasource
import me.jorgecasariego.pororopeliculas.ui.login.LoginViewModel
import me.jorgecasariego.pororopeliculas.ui.movie.MoviesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object Modules {
    private val repositoryModule = module {
        single { ConfigRepository(get()) as IConfigRepository }
        single { MovieRepository(get(), get()) as IMovieRepository}
    }

    private val viewModelModule = module {
        single { LoginViewModel(get()) }
        viewModel { MoviesViewModel(get(), get()) }
    }

    private val dataSourceModule = module {
        single { LocalConfigDataSource(androidApplication()) as ILocalConfigDataSource}
        single { MovieLocalDatasource(androidApplication()) as IMovieLocalDatasource}
        single { MovieRemoteDatasource(get()) as IMovieRemoteDatasource}
    }

    private val coroutinesContextProviderModule = module {
        single { CoroutineContextProvider() }
    }

    private val networkModule = module {
        factory { provideOkHttpClient() }
        factory { provideRetrofit(get()) }
        factory { provideMoviewDbApi(get()) }
    }

    val all: List<Module> = listOf(
            coroutinesContextProviderModule,
            repositoryModule,
            viewModelModule,
            dataSourceModule,
            networkModule
    )
}