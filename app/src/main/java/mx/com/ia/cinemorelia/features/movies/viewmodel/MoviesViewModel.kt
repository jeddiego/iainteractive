package mx.com.ia.cinemorelia.features.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MoviesViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Fragmento cartelera"
    }
    val text: LiveData<String> = _text
}