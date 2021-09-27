package mx.com.ia.cinemorelia.features.movies.usecase

import mx.com.ia.cinemorelia.core.Result
import mx.com.ia.cinemorelia.features.movies.models.MoviesResponseModel
import mx.com.ia.cinemorelia.features.movies.services.IMoviesNetworkService

class MoviesUseCases(
    private val networkService: IMoviesNetworkService
): IMoviesUseCases {
    override fun getMovies(): Result<MoviesResponseModel> {
        val requestMovies = networkService.getMovies()
        if(requestMovies.hasError) {
            return Result(null, requestMovies.error)
        }

        var posterUrl = ""
            requestMovies.result!!.routes.forEach { route ->
            if(route.code == "poster") {
                posterUrl = route.sizes.large ?: ""
            }
        }

        requestMovies.result.movies.forEach { movie ->
            movie.posterUrl = posterUrl
        }

        return Result(requestMovies.result)
    }
}

interface IMoviesUseCases {
    fun getMovies(): Result<MoviesResponseModel>
}