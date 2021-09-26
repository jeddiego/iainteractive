package mx.com.ia.cinemorelia.features.login.models

data class LoginBodyModel(
    val country_code: String,
    val username: String,
    val password: String,
    val grant_type: String,
    val client_id: String,
    val client_secret: String
)
