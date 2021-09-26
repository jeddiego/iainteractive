package mx.com.ia.cinemorelia.features.login.usecase

import mx.com.ia.cinemorelia.core.Result
import mx.com.ia.cinemorelia.features.login.models.LoginBodyModel
import mx.com.ia.cinemorelia.features.login.services.ILoginNetworkService
import mx.com.ia.cinemorelia.policy.CinemoreliaPolicy.Companion.key_access_token
import mx.com.ia.cinemorelia.policy.CinemoreliaPolicy.Companion.key_token_type
import mx.com.ia.cinemorelia.policy.CinemoreliaPolicy.Companion.key_user_logged
import mx.com.ia.cinemorelia.service.CinemoreliaSharedPreference

class LoginUseCases(
    private val networkService: ILoginNetworkService,
    private val sharedPreference: CinemoreliaSharedPreference
): ILoginUseCases {
    override fun login(body: LoginBodyModel): Result<Boolean> {
        val request = networkService.login(body)

        if(request.hasError) {
            return Result(false, request.error)
        }

        sharedPreference.saveValue(key_token_type, request.result!!.token_type)
        sharedPreference.saveValue(key_access_token, request.result.access_token)
        sharedPreference.saveValue(key_user_logged, request.result.username)

        return Result(true)
    }
}

interface ILoginUseCases {
    fun login(body: LoginBodyModel): Result<Boolean>
}