package mx.com.ia.cinemorelia.features.login.usecase

import mx.com.ia.cinemorelia.core.Result
import mx.com.ia.cinemorelia.features.login.models.LoginBodyModel
import mx.com.ia.cinemorelia.features.login.services.ILoginNetworkService

class LoginUseCases(
    private val networkService: ILoginNetworkService
): ILoginUseCases {
    override fun login(body: LoginBodyModel): Result<Boolean> {
        val request = networkService.login(body)
        if(request.hasError) {
            return Result(false, request.error)
        }

        return Result(true)
    }
}

interface ILoginUseCases {
    fun login(body: LoginBodyModel): Result<Boolean>
}