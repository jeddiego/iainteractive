package mx.com.ia.cinemorelia.features.profile.models

import com.google.gson.annotations.SerializedName

data class ProfileBodyModel(
    @SerializedName("card_number")
    val card: Long,
    @SerializedName("country_code")
    val countryCode: String,
    @SerializedName("transaction_include")
    val transactionInclude: Boolean
)
