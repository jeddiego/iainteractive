package mx.com.ia.cinemorelia.features.module

import mx.com.ia.cinemorelia.BuildConfig
import mx.com.ia.cinemorelia.features.profile.services.IProfileNetworkService
import mx.com.ia.cinemorelia.features.profile.services.ProfileNetworkService
import mx.com.ia.cinemorelia.features.profile.services.ProfileRetrofitDefinition
import mx.com.ia.cinemorelia.features.profile.usecase.IProfileUseCases
import mx.com.ia.cinemorelia.features.profile.usecase.ProfileUseCases
import mx.com.ia.cinemorelia.features.profile.viewmodel.ProfileViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val profileModule = module {
    viewModel { ProfileViewModel(get(), get()) }
    factory { ProfileUseCases(get(), get()) as IProfileUseCases }
    single { ProfileNetworkService(get()) as IProfileNetworkService }
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
            .create(ProfileRetrofitDefinition::class.java)
                as ProfileRetrofitDefinition
    }
}