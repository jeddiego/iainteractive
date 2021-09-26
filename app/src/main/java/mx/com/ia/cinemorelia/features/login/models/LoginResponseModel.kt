package mx.com.ia.cinemorelia.features.login.models

import com.google.gson.annotations.SerializedName

data class LoginResponseModel(
    val access_token: String,
    val token_type: String,
    val expires_in: Long,
    val refresh_token: String,
    @SerializedName("as:client_id")
    val client_id: String,
    val username: String,
    val country_code: String,
    @SerializedName(".issued")
    val issued: String,
    @SerializedName(".expires")
    val expires: String
)
