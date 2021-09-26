package mx.com.ia.cinemorelia.core

data class Error(
    val errorCode: String,
    val errorMessage: String,
    val innerErrorList: List<Error> = emptyList(),
    val exception: Exception? = null
)
