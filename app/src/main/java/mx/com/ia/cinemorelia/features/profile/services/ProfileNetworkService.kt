package mx.com.ia.cinemorelia.features.profile.services

import mx.com.ia.cinemorelia.R
import mx.com.ia.cinemorelia.core.Error
import mx.com.ia.cinemorelia.core.Result
import mx.com.ia.cinemorelia.features.profile.models.ProfileModel
import mx.com.ia.cinemorelia.utils.IFeatureProvider
import org.json.JSONObject
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Call
import retrofit2.http.*

class ProfileNetworkService(
    private val retrofit: ProfileRetrofitDefinition
) : IProfileNetworkService, KoinComponent {
    private val featureProvider: IFeatureProvider by inject()

    override fun getProfile(token: String): Result<ProfileModel> {
        return try {
            val result = retrofit.getProfile("bearer $token").execute()
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

interface IProfileNetworkService {
    fun getProfile(token: String): Result<ProfileModel>
}

interface ProfileRetrofitDefinition {
    @Headers("api_key: stage_HNYh3RaK_Test")
    @GET("members/profile?country_code=MX")
    fun getProfile(@Header("authorization") token: String): Call<ProfileModel>
}