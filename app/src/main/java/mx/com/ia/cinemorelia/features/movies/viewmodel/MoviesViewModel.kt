package mx.com.ia.cinemorelia.features.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.com.ia.cinemorelia.core.IDispatcher
import mx.com.ia.cinemorelia.core.Result
import mx.com.ia.cinemorelia.datasource.entities.MoviesEntity
import mx.com.ia.cinemorelia.features.movies.usecase.IMoviesUseCases

class MoviesViewModel(
    private val dispatcher: IDispatcher,
    private val useCases: IMoviesUseCases
): ViewModel() {
    private val _getMovies = MutableLiveData<StateActions>()
    private val _getMovie = MutableLiveData<StateActions>()

    fun action(action: Actions) {
        when (action) {
            is Actions.GetMovies -> {
                _getMovies.value = StateActions.Loading
                viewModelScope.launch(dispatcher.getIOThread()) {
                    val result = useCases.getMovies()
                    _getMovies.postValue(StateActions.GetMoviesResult(result))
                }
            }
            is Actions.GetMovie -> {
                _getMovie.value = StateActions.Loading
                viewModelScope.launch(dispatcher.getIOThread()) {
                    val result = useCases.getMovieById(action.idMovie)
                    _getMovie.postValue(StateActions.GetMovieResult(result))
                }
            }
        }
    }

    fun getMovies(): LiveData<StateActions> = _getMovies
    fun getMovie(): LiveData<StateActions> = _getMovie

    sealed class Actions {
        object GetMovies: Actions()
        data class GetMovie(val idMovie: Long): Actions()
    }

    sealed class StateActions {
        object Loading : StateActions()
        data class GetMoviesResult(val result: Result<List<MoviesEntity>>): StateActions()
        data class GetMovieResult(val result: Result<MoviesEntity>): StateActions()
    }
}