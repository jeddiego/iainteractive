package mx.com.ia.cinemorelia.core

import java.lang.IllegalStateException

class Result<T> (val result: T?, val error: Error? = null){
    init {
        if(result == null && error == null) {
            throw IllegalStateException("Result must have result or error")
        }
    }

    val hasError = error != null

    val isSuccess: Boolean = !hasError
}