package mx.com.ia.cinemorelia.features.movies.module

import mx.com.ia.cinemorelia.BuildConfig
import mx.com.ia.cinemorelia.features.movies.services.IMoviesNetworkService
import mx.com.ia.cinemorelia.features.movies.services.MoviesNetworkService
import mx.com.ia.cinemorelia.features.movies.services.MoviesRetrofitDefinition
import mx.com.ia.cinemorelia.features.movies.usecase.IMoviesUseCases
import mx.com.ia.cinemorelia.features.movies.usecase.MoviesUseCases
import mx.com.ia.cinemorelia.features.movies.viewmodel.MoviesViewModel
import mx.com.ia.cinemorelia.features.profile.services.ProfileRetrofitDefinition
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val moviesModule = module {
    viewModel { MoviesViewModel(get(), get()) }
    factory { MoviesUseCases(get()) as IMoviesUseCases }
    single { MoviesNetworkService(get()) as IMoviesNetworkService }
    single {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl("${BuildConfig.URL_BASE}/")
            .build()
            .create(MoviesRetrofitDefinition::class.java)
                as MoviesRetrofitDefinition
    }
}