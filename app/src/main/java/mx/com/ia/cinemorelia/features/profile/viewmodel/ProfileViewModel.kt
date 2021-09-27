package mx.com.ia.cinemorelia.features.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.com.ia.cinemorelia.core.IDispatcher
import mx.com.ia.cinemorelia.core.Result
import mx.com.ia.cinemorelia.features.profile.models.ProfileModel
import mx.com.ia.cinemorelia.features.profile.usecase.IProfileUseCases

class ProfileViewModel(
    private val dispatcher: IDispatcher,
    private val useCases: IProfileUseCases
): ViewModel() {
    private val _getUserData = MutableLiveData<StateActions>()

    fun action(action: Actions) {
        when (action) {
            is Actions.GetUserData -> {
                _getUserData.value = StateActions.Loading
                viewModelScope.launch(dispatcher.getIOThread()) {
                    val result = useCases.getData()
                    _getUserData.postValue(StateActions.GetUserDataResult(result))
                }
            }
        }
    }

    fun getUserData(): LiveData<StateActions> = _getUserData

    sealed class Actions {
        object GetUserData: Actions()
    }

    sealed class StateActions {
        object Loading : StateActions()
        data class GetUserDataResult(val result: Result<ProfileModel>): StateActions()
    }
}