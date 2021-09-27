package mx.com.ia.cinemorelia.features.movies.models

data class MoviesResponseModel(
    val movies: List<MoviesModel>,
    val routes: List<MoviesRoutesModel>
)
