package mx.com.ia.cinemorelia.features.movies.services

import mx.com.ia.cinemorelia.datasource.db.CinemoreliaDatabaseRoom
import mx.com.ia.cinemorelia.datasource.entities.MoviesEntity

class MoviesLocalService(
    private val room: CinemoreliaDatabaseRoom
): IMoviesLocalService {
    override fun insertAll(movies: List<MoviesEntity>) {
        room.MoviesDao().insertAll(movies)
    }

    override fun getAll(): List<MoviesEntity> {
        return room.MoviesDao().getAll()
    }
}

interface IMoviesLocalService {
    fun insertAll(movies: List<MoviesEntity>)
    fun getAll(): List<MoviesEntity>
}