package mx.com.ia.cinemorelia.features.movies.usecase

import mx.com.ia.cinemorelia.core.Result
import mx.com.ia.cinemorelia.datasource.entities.MoviesEntity
import mx.com.ia.cinemorelia.features.movies.MoviesPolicy
import mx.com.ia.cinemorelia.features.movies.services.IMoviesLocalService
import mx.com.ia.cinemorelia.features.movies.services.IMoviesNetworkService

class MoviesUseCases(
    private val networkService: IMoviesNetworkService,
    private val localService: IMoviesLocalService
) : IMoviesUseCases {
    override fun getMovies(): Result<List<MoviesEntity>> {
        val requestMovies = networkService.getMovies()
        val validateResponse = MoviesPolicy.validateRequestMovies(requestMovies)
        if (validateResponse.hasError) {
            return Result(null, validateResponse.error)
        }

        val moviesEntity = if (validateResponse.result!!) {
            var posterUrl = ""
            var trailerUrl = ""
            requestMovies.result!!.routes.forEach { route ->
                if (route.code == "poster") {
                    posterUrl = route.sizes.large ?: ""
                }
                if(route.code == "trailer_mp4") {
                    trailerUrl = route.sizes.medium ?: ""
                }
            }

            val moviesEntity = mutableListOf<MoviesEntity>()

            requestMovies.result.movies.forEach { movie ->
                movie.posterUrl = posterUrl
                movie.trailerUrl = trailerUrl

                var poster = ""
                var trailer = ""
                movie.media.forEach {
                    if (it.code == "poster") {
                        poster = it.resource
                    }
                    if (it.code == "trailer_mp4") {
                        trailer = it.resource
                    }
                }

                moviesEntity.add(
                    MoviesEntity(
                        id = movie.id,
                        poster = poster,
                        trailer = trailer,
                        name = movie.name,
                        genre = movie.genre,
                        synopsis = movie.synopsis,
                        length = movie.length,
                        rating = movie.rating,
                        posterUrl = movie.posterUrl,
                        trailerUrl = trailerUrl
                    )
                )
            }

            localService.insertAll(moviesEntity)
            moviesEntity
        } else {
            localService.getAll()
        }

        return Result(moviesEntity)
    }

    override fun getMovieById(idMovie: Long): Result<MoviesEntity> {
        val movie = localService.getMovieById(idMovie)
        return Result(movie)
    }
}

interface IMoviesUseCases {
    fun getMovies(): Result<List<MoviesEntity>>
    fun getMovieById(idMovie: Long): Result<MoviesEntity>
}