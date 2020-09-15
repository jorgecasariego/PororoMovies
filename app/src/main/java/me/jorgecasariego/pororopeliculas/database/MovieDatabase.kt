package me.jorgecasariego.pororopeliculas.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.jorgecasariego.pororopeliculas.database.dao.MovieDao
import me.jorgecasariego.pororopeliculas.model.Movie
import me.jorgecasariego.pororopeliculas.repository.config.IConfigRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

@Database(
        entities = [Movie::class],
        version = 1,
        exportSchema = false
)

abstract class MovieDatabase: RoomDatabase() {
    abstract  fun movieDao(): MovieDao

    companion object: KoinComponent {
        private var INSTANCE: MovieDatabase? = null
        private val configRepository by inject<IConfigRepository>()

        fun getInstance(context: Context): MovieDatabase? {

            if (INSTANCE == null) {
                synchronized(MovieDatabase::class) {
                    val currentLoggedUser = configRepository.getCurrentUserInfo() ?: return null

                    INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            MovieDatabase::class.java,
                            "${currentLoggedUser.username}.db"
                    ).build()
                }
            }

            return INSTANCE
        }
    }

    fun deleteCurrentLogeedUserDatabase(context: Context) {
        val currentLoggedUser = configRepository.getCurrentUserInfo() ?: return

        val dbFile = context.getDatabasePath("$currentLoggedUser.db")

        if (dbFile.exists()) {
            dbFile.delete()
        }
    }

    /**
     * This will be used after logout
     */
    fun destroyInstance() {
        INSTANCE?.let {
            if (it.isOpen) {
                it.close()
            }
        }

        INSTANCE = null
    }
}