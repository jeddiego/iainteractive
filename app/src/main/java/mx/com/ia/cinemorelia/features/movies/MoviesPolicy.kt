package mx.com.ia.cinemorelia.features.movies

import mx.com.ia.cinemorelia.core.Result
import mx.com.ia.cinemorelia.features.movies.models.MoviesResponseModel

class MoviesPolicy {
    companion object {
        fun validateRequestMovies(requestMovies: Result<MoviesResponseModel>): Result<Boolean> {
            if(requestMovies.hasError) {
                return if(requestMovies.error!!.errorMessage.contains("No address associated with hostname", ignoreCase = true)) {
                    Result(false)
                } else {
                    Result(false, requestMovies.error)
                }
            }
            return Result(true)
        }

    }
}