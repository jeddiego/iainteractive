package mx.com.ia.cinemorelia.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase
import mx.com.ia.cinemorelia.datasource.daos.MoviesDao
import mx.com.ia.cinemorelia.datasource.entities.MoviesEntity

@Database(entities = [
    MoviesEntity::class
                     ], version = 1, exportSchema = false)
abstract class CinemoreliaDatabaseRoom: RoomDatabase() {
    abstract fun MoviesDao(): MoviesDao
}