package me.jorgecasariego.pororopeliculas.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import kotlinx.coroutines.coroutineScope
import me.jorgecasariego.pororopeliculas.base.Constants
import me.jorgecasariego.pororopeliculas.model.Movie
import me.jorgecasariego.pororopeliculas.repository.movies.IMovieRepository
import me.jorgecasariego.pororopeliculas.workers.controller.WorkerController
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetMoviesWorker(appContext: Context, workerParams: WorkerParameters) :
        CoroutineWorker(appContext, workerParams), KoinComponent {

    private val TAG = this::class.java.simpleName
    private val movieRepository: IMovieRepository by inject()
    private var runAttempt: Int = 0
    private val MAX_RETRY: Int = 1

    override suspend fun doWork(): Result = coroutineScope {
        execute()
    }

    suspend fun execute(): Result {
        val category = inputData.getString(WorkerController.WORKER_PARAMETER_CATEGORY)
                ?: return Result.failure()

        kotlin.runCatching {
            movieRepository.getMoviesFromServer(
                    category = category,
                    language = Constants.LANGUAGE,
                    page = Constants.PAGE,
                    apiKey = Constants.API_KEY)
        }.onSuccess { response ->
            val movieList: ArrayList<Movie> = response.results
            movieRepository.insertMovies(movieList.toTypedArray())
        }.onFailure {
            Log.e(TAG, "Fail to get movies: ${it.localizedMessage}")
            manageFailure()
        }

        return Result.success()
    }

    private fun manageFailure(): Result {
        return if (runAttempt < MAX_RETRY) {
            runAttempt += 1
            Result.retry()
        } else {
            Result.failure()
        }
    }

}