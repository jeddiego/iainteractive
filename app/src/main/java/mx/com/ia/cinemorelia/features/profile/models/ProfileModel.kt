package mx.com.ia.cinemorelia.features.profile.models

import com.google.gson.annotations.SerializedName

data class ProfileModel (
     val email: String,
     @SerializedName("first_name")
     val firstName: String,
     @SerializedName("last_name")
     val lastName: String,
     @SerializedName("phone_number")
     val phone: String,
     @SerializedName("profile_picture")
     val picture: String,
     @SerializedName("card_number")
     val card: Long
)