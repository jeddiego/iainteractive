package mx.com.ia.cinemorelia.features.movies.models

import com.google.gson.annotations.SerializedName

data class MoviesRouteSizesModel(
    val large: String?,
    val medium: String?,
    val small: String?,
    @SerializedName("x-large")
    val xlarge: String?
)
