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

    fun action(action: Actions) {
        when (action) {
            is Actions.GetMovies -> {
                _getMovies.value = StateActions.Loading
                viewModelScope.launch(dispatcher.getIOThread()) {
                    val result = useCases.getMovies()
                    _getMovies.postValue(StateActions.GetMoviesResult(result))
                }
            }
        }
    }

    fun getMovies(): LiveData<StateActions> = _getMovies

    sealed class Actions {
        object GetMovies: Actions()
    }

    sealed class StateActions {
        object Loading : StateActions()
        data class GetMoviesResult(val result: Result<List<MoviesEntity>>): StateActions()
    }
}