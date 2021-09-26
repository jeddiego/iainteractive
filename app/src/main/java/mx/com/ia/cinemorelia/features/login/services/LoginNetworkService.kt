package mx.com.ia.cinemorelia.features.login.services

import mx.com.ia.cinemorelia.R
import mx.com.ia.cinemorelia.core.Error
import mx.com.ia.cinemorelia.core.Result
import mx.com.ia.cinemorelia.features.login.models.LoginBodyModel
import mx.com.ia.cinemorelia.features.login.models.LoginResponseModel
import mx.com.ia.cinemorelia.utils.IFeatureProvider
import org.json.JSONObject
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


class LoginNetworkService(
    private val retrofit: LoginRetrofitDefinition
) : ILoginNetworkService, KoinComponent {
    private val featureProvider: IFeatureProvider by inject()

    override fun login(body: LoginBodyModel): Result<Any> {
        return try {
            val result = retrofit.login(
                "country_code=${body.country_code}&username=${body.username}&password=${body.password}&grant_type=${body.grant_type}&client_id=${body.client_id}&client_secret=${body.client_secret}"
            ).execute()
            if (result.isSuccessful) {
                Result(result.body())
            } else {
                val jObjError = JSONObject(result.errorBody()!!.string())
                Result(
                    null,
                    Error(
                        featureProvider.getAppContext().getString(R.string.error_title_login),
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

interface ILoginNetworkService {
    fun login(body: LoginBodyModel): Result<Any>
}

interface LoginRetrofitDefinition {
    @Headers("api_key: stage_HNYh3RaK_Test")
    @POST("oauth/token")
    fun login(
        @Body data: String
    ): Call<LoginResponseModel>
}