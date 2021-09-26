package mx.com.ia.cinemorelia.features.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.com.ia.cinemorelia.core.IDispatcher
import mx.com.ia.cinemorelia.core.Result
import mx.com.ia.cinemorelia.features.login.models.LoginBodyModel
import mx.com.ia.cinemorelia.features.login.usecase.ILoginUseCases

class LoginViewModel(
    private val dispatcher: IDispatcher,
    private val useCases: ILoginUseCases
): ViewModel() {
    private val _login = MutableLiveData<StateActions>()

    fun action(action: Actions) {
        when (action) {
            is Actions.Login -> {
                _login.value = StateActions.Loading
                viewModelScope.launch(dispatcher.getIOThread()) {
                    val result = useCases.login(action.body)
                    _login.postValue(StateActions.LoginResult(result))
                }
            }
        }
    }

    fun login(): LiveData<StateActions> = _login

    sealed class Actions {
        data class Login(val body: LoginBodyModel): Actions()
    }

    sealed class StateActions {
        object Loading : StateActions()
        data class LoginResult(val result: Result<Boolean>): StateActions()
    }
}