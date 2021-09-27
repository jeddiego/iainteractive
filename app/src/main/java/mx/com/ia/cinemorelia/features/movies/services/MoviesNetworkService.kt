package mx.com.ia.cinemorelia.features.movies.services

import mx.com.ia.cinemorelia.R
import mx.com.ia.cinemorelia.core.Error
import mx.com.ia.cinemorelia.core.Result
import mx.com.ia.cinemorelia.features.movies.models.MoviesResponseModel
import mx.com.ia.cinemorelia.utils.IFeatureProvider
import org.json.JSONObject
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Call
import retrofit2.http.*

class MoviesNetworkService(
    private val retrofit: MoviesRetrofitDefinition
) : IMoviesNetworkService, KoinComponent {
    private val featureProvider: IFeatureProvider by inject()

    override fun getMovies(): Result<MoviesResponseModel> {
        return try {
            val result = retrofit.getMovies().execute()
            if (result.isSuccessful) {
                Result(result.body())
            } else {
                val jObjError = JSONObject(result.errorBody()!!.string())
                Result(
                    null,
                    Error(
                        featureProvider.getAppContext().getString(R.string.error_title),
                        jObjError.getJSONObject("error").getString("message")
                    )
                )
            }
        } catch (e: Exception) {
            Result(
                null,
                Error(
                    featureProvider.getAppContext().getString(R.string.exception_title_login),
                    featureProvider.getAppContext()
                        .getString(R.string.exception_body_login, e.toString())
                )
            )
        }
    }
}

interface IMoviesNetworkService {
    fun getMovies(): Result<MoviesResponseModel>
}

interface MoviesRetrofitDefinition {
    @Headers("api_key: stage_HNYh3RaK_Test")
    @GET("movies?country_code=MX&cinema=61")
    fun getMovies(): Call<MoviesResponseModel>
}