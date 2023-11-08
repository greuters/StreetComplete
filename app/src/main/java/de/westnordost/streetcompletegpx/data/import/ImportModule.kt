package de.westnordost.streetcompletegpx.data.import

import org.koin.dsl.module

val importModule = module {
    factory { GpxImporter() }
}
