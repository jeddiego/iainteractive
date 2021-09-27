package mx.com.ia.cinemorelia.features.movies.models

data class MoviesModel(
    val media: List<MovieMediaModel>,
    val name: String,
    val id: Long,
    val genre: String,
    val synopsis: String,
    val length: String,
    val rating: String,
    var posterUrl: String
)
