package mx.com.ia.cinemorelia.features.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Fragmento perfil"
    }
    val text: LiveData<String> = _text
}