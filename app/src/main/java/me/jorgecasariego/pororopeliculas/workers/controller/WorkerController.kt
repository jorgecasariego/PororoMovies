package me.jorgecasariego.pororopeliculas.workers.controller

import android.content.Context
import androidx.work.*
import me.jorgecasariego.pororopeliculas.workers.GetMoviesWorker

object WorkerController {
    const val WORKER_GET_MOVIES = "WORKER_GET_MOVIES"
    const val WORKER_PARAMETER_CATEGORY = "WORKER_PARAMETER_CATEGORY"

    fun startGetMovies(
            applicationContext: Context,
            userId: String,
            category: String
    ) {
        val inputData = Data.Builder()
                .putString(WORKER_PARAMETER_CATEGORY, category)
                .build()

        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val workRequest =
                OneTimeWorkRequestBuilder<GetMoviesWorker>()
                        .setConstraints(constraints)
                        .setInputData(inputData)
                        .addTag("$WORKER_GET_MOVIES.$userId")
                        .build()

        val workManager = WorkManager.getInstance(applicationContext)

        workManager.beginUniqueWork(
                "$WORKER_GET_MOVIES.$userId",
                ExistingWorkPolicy.KEEP,
                workRequest
        ).enqueue()
    }
}