package de.westnordost.streetcompletegpx.data.osm.mapdata

import org.koin.dsl.module

val mapDataModule = module {
    factory { ElementDao(get(), get(), get()) }
    factory { MapDataDownloader(get(), get()) }
    factory { NodeDao(get()) }
    factory { RelationDao(get()) }
    factory { WayDao(get()) }

    single { MapDataController(get(), get(), get(), get(), get(), get(), get()) }
}
