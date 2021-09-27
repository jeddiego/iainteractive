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
            var trailer = ""
            requestMovies.result!!.routes.forEach { route ->
                if (route.code == "poster") {
                    posterUrl = route.sizes.large ?: ""
                }
                if(route.code == "trailer_mp4") {
                    trailer = route.sizes.medium ?: ""
                }
            }

            val moviesEntity = mutableListOf<MoviesEntity>()

            requestMovies.result.movies.forEach { movie ->
                movie.posterUrl = posterUrl

                var media = ""
                movie.media.forEach {
                    if (it.code == "poster") {
                        media = it.resource
                    }
                }

                moviesEntity.add(
                    MoviesEntity(
                        movie.id,
                        media,
                        trailer,
                        movie.name,
                        movie.genre,
                        movie.synopsis,
                        movie.length,
                        movie.rating,
                        movie.posterUrl
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
}

interface IMoviesUseCases {
    fun getMovies(): Result<List<MoviesEntity>>
}