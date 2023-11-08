package de.westnordost.streetcompletegpx.data.download

import de.westnordost.streetcompletegpx.data.download.strategy.MobileDataAutoDownloadStrategy
import de.westnordost.streetcompletegpx.data.download.strategy.WifiAutoDownloadStrategy
import de.westnordost.streetcompletegpx.data.download.tiles.DownloadedTilesController
import de.westnordost.streetcompletegpx.data.download.tiles.DownloadedTilesDao
import de.westnordost.streetcompletegpx.data.download.tiles.DownloadedTilesSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

val downloadModule = module {
    factory { DownloadedTilesDao(get()) }
    factory { MobileDataAutoDownloadStrategy(get(), get()) }
    factory { WifiAutoDownloadStrategy(get(), get()) }

    single { Downloader(get(), get(), get(), get(), get(named("SerializeSync"))) }

    single<DownloadProgressSource> { get<DownloadController>() }
    single { DownloadController(get()) }

    single<DownloadedTilesSource> { get<DownloadedTilesController>() }
    single { DownloadedTilesController(get()) }
}
