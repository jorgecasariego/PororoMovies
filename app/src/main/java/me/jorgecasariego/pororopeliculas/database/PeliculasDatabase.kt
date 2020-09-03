package me.jorgecasariego.pororopeliculas.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import me.jorgecasariego.pororopeliculas.database.DbContract.PeliculaEntry.Companion.DESCRIPCION_PELICULA
import me.jorgecasariego.pororopeliculas.database.DbContract.PeliculaEntry.Companion.ES_FAVORITA
import me.jorgecasariego.pororopeliculas.database.DbContract.PeliculaEntry.Companion.ID_PELICULA
import me.jorgecasariego.pororopeliculas.database.DbContract.PeliculaEntry.Companion.NOMBRE_TABLA
import me.jorgecasariego.pororopeliculas.database.DbContract.PeliculaEntry.Companion.POSTER_PATH
import me.jorgecasariego.pororopeliculas.database.DbContract.PeliculaEntry.Companion.RELEASE_DATE
import me.jorgecasariego.pororopeliculas.database.DbContract.PeliculaEntry.Companion.TITULO_PELICULA
import me.jorgecasariego.pororopeliculas.model.Model

class PeliculasDatabase(val context: Context):
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        Toast.makeText(context,
                "Creando base de datos con version $DATABASE_VERSION", Toast.LENGTH_LONG).show()

        db?.execSQL(SQL_CREATE_PELICULAS_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Toast.makeText(context, "Actualizando BD a version $DATABASE_VERSION", Toast.LENGTH_LONG).show()
        db?.execSQL(SQL_DROP_TABLE)
        onCreate(db)
    }

    fun insertarPeliculaFavorita(pelicula: Model.Movie): Boolean {
        val db = writableDatabase

        val contenidoPelicula = ContentValues()

        contenidoPelicula.put(DbContract.PeliculaEntry.ID_PELICULA, pelicula.id)
        contenidoPelicula.put(DbContract.PeliculaEntry.TITULO_PELICULA, pelicula.title)
        contenidoPelicula.put(DbContract.PeliculaEntry.DESCRIPCION_PELICULA, pelicula.overview)
        contenidoPelicula.put(DbContract.PeliculaEntry.POSTER_PATH, pelicula.poster_path)
        contenidoPelicula.put(DbContract.PeliculaEntry.RELEASE_DATE, pelicula.release_date)
        contenidoPelicula.put(DbContract.PeliculaEntry.ES_FAVORITA, pelicula.es_favorito)

        val idNota = db.insertWithOnConflict(
                DbContract.PeliculaEntry.NOMBRE_TABLA,
                null,
                contenidoPelicula,
                SQLiteDatabase.CONFLICT_IGNORE)

        if (idNota.toInt() == -1) {
            Toast.makeText(context, "Actualizando Favorito", Toast.LENGTH_LONG).show()
            db.update(
                    DbContract.PeliculaEntry.NOMBRE_TABLA,
                    contenidoPelicula,
                    "${DbContract.PeliculaEntry.ID_PELICULA}=?",
                    arrayOf(pelicula.id.toString())
            )
        }

        return idNota > -1
    }

    fun getPeliculasFavoritas(): ArrayList<Model.Movie> {
        val peliculas = ArrayList<Model.Movie>()
        val db = writableDatabase
        val query = "SELECT * FROM ${DbContract.PeliculaEntry.NOMBRE_TABLA}"

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(query, null)
        } catch (e: SQLException) {
            db.execSQL(SQL_CREATE_PELICULAS_ENTRIES)
            return ArrayList<Model.Movie>()
        }

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val idPelicula = cursor.getInt(cursor.getColumnIndex(ID_PELICULA))
                val titulo = cursor.getString(cursor.getColumnIndex(TITULO_PELICULA))
                val posterPath = cursor.getString(cursor.getColumnIndex(POSTER_PATH))
                val descripcion = cursor.getString(cursor.getColumnIndex(DESCRIPCION_PELICULA))
                val releaseDate = cursor.getString(cursor.getColumnIndex(RELEASE_DATE))
                // 1 = true - 0 = false
                val esFavorito =
                        cursor.getInt(cursor.getColumnIndex(ES_FAVORITA)) == 1

                val pelicula = Model.Movie(
                        idPelicula, titulo,posterPath,"en",titulo,
                        descripcion,releaseDate,esFavorito)
                peliculas.add(pelicula)
                cursor.moveToNext()
            }
        }

        return peliculas
    }

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "peliculas.db"

        private val SQL_CREATE_PELICULAS_ENTRIES =
                "CREATE TABLE " + DbContract.PeliculaEntry.NOMBRE_TABLA + " ( " +
                DbContract.PeliculaEntry.ID_PELICULA + " INTEGER PRIMARY KEY, " +
                DbContract.PeliculaEntry.TITULO_PELICULA + " TEXT, " +
                DbContract.PeliculaEntry.DESCRIPCION_PELICULA + " TEXT, " +
                DbContract.PeliculaEntry.POSTER_PATH + " TEXT, " +
                DbContract.PeliculaEntry.RELEASE_DATE + " TEXT, " +
                DbContract.PeliculaEntry.ES_FAVORITA + " INTEGER )"

        private val SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + NOMBRE_TABLA
    }
}