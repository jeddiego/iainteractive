package mx.com.ia.cinemorelia.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface IDispatcher {
    fun getMainThread(): CoroutineDispatcher

    fun getIOThread(): CoroutineDispatcher
}

class AppDispatchers: IDispatcher {
    override fun getMainThread(): CoroutineDispatcher = Dispatchers.Main

    override fun getIOThread(): CoroutineDispatcher = Dispatchers.IO
}