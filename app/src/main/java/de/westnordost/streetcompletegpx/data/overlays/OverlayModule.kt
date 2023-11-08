package de.westnordost.streetcompletegpx.data.overlays

import org.koin.dsl.module

val overlayModule = module {
    single<SelectedOverlaySource> { get<SelectedOverlayController>() }
    single { SelectedOverlayController(get(), get()) }

    factory { SelectedOverlayStore(get()) }
}
