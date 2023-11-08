package de.westnordost.streetcompletegpx.data.maptiles

import org.koin.dsl.module

val maptilesModule = module {
    factory { MapTilesDownloader(get(), get()) }

    single { MapTilesDownloadCacheConfig(get()) }
}
