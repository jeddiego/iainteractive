package mx.com.ia.cinemorelia.features.module

import mx.com.ia.cinemorelia.features.profile.viewmodel.ProfileViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
    viewModel { ProfileViewModel() }
}