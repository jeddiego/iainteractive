package mx.com.ia.cinemorelia.datasource.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.com.ia.cinemorelia.datasource.entities.MoviesEntity

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(cedis: List<MoviesEntity>)

    @Query("SELECT * FROM movies")
    fun getAll(): List<MoviesEntity>
}