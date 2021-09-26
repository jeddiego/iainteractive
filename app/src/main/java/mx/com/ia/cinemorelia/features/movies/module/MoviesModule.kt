package mx.com.ia.cinemorelia.features.movies.module

import mx.com.ia.cinemorelia.features.movies.viewmodel.MoviesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moviesModule = module {
    viewModel { MoviesViewModel() }
}