package mx.com.ia.cinemorelia.features.login.module

import mx.com.ia.cinemorelia.BuildConfig
import mx.com.ia.cinemorelia.features.login.services.ILoginNetworkService
import mx.com.ia.cinemorelia.features.login.services.LoginNetworkService
import mx.com.ia.cinemorelia.features.login.services.LoginRetrofitDefinition
import mx.com.ia.cinemorelia.features.login.usecase.ILoginUseCases
import mx.com.ia.cinemorelia.features.login.usecase.LoginUseCases
import mx.com.ia.cinemorelia.features.login.viewmodel.LoginViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

val loginModule = module {
    viewModel { LoginViewModel(get(), get()) as LoginViewModel }
    factory { LoginUseCases(get()) as ILoginUseCases }
    single { LoginNetworkService(get()) as ILoginNetworkService }
    single {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl("${BuildConfig.URL_BASE}/")
            .build()
            .create(LoginRetrofitDefinition::class.java)
                as LoginRetrofitDefinition
    }
}