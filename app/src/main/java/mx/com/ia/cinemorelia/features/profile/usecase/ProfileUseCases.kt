package mx.com.ia.cinemorelia.features.profile.usecase

import mx.com.ia.cinemorelia.core.Result
import mx.com.ia.cinemorelia.features.profile.models.ProfileBodyModel
import mx.com.ia.cinemorelia.features.profile.models.ProfileModel
import mx.com.ia.cinemorelia.features.profile.services.IProfileNetworkService
import mx.com.ia.cinemorelia.policy.CinemoreliaPolicy.Companion.key_access_token
import mx.com.ia.cinemorelia.service.CinemoreliaSharedPreference

class ProfileUseCases(
    private val networkService: IProfileNetworkService,
    private val sharedPreference: CinemoreliaSharedPreference
): IProfileUseCases {
    override fun getData(): Result<ProfileModel> {
        val token = sharedPreference.getStringValue(key_access_token)

        val request = networkService.getProfile(token!!)
        if(request.hasError) {
            return Result(null, request.error)
        }

        return Result(request.result)
    }
}

interface IProfileUseCases {
    fun getData(): Result<ProfileModel>
}