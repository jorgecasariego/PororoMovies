package me.jorgecasariego.pororopeliculas.database


object DbContract {
    // Definimos el contenido de la tabla de peliculas
    class PeliculaEntry {
        companion object {
            val NOMBRE_TABLA = "Pelicula"
            //Columnas
            val ID_PELICULA = "id_pelicula"
            val TITULO_PELICULA = "titulo_pelicula"
            val POSTER_PATH = "poster_path"
            val DESCRIPCION_PELICULA = "descripcion_pelicula"
            val RELEASE_DATE = "release_date"
            val ES_FAVORITA = "es_favorita"
        }
    }
}