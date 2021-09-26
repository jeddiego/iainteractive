package mx.com.ia.cinemorelia.module

import mx.com.ia.cinemorelia.core.AppDispatchers
import mx.com.ia.cinemorelia.core.IDispatcher
import mx.com.ia.cinemorelia.service.CinemoreliaSharedPreference
import mx.com.ia.cinemorelia.utils.FeatureProvider
import mx.com.ia.cinemorelia.utils.IFeatureProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val cinemoreliaModule = module {
    single { AppDispatchers() as IDispatcher }
    factory { FeatureProvider(androidContext()) as IFeatureProvider }
    single { CinemoreliaSharedPreference(androidContext()) }
/*    single {
        Room.databaseBuilder(
            androidApplication(),
            CinemoreliaDatabaseRoom::class.java,
            "CinemoreliaDb"
        ).build()
    }*/
}