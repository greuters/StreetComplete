package de.westnordost.streetcompletegpx.screens.main

import de.westnordost.streetcompletegpx.data.location.RecentLocationStore
import de.westnordost.streetcompletegpx.util.location.LocationAvailabilityReceiver
import org.koin.dsl.module

val mainModule = module {
    single { LocationAvailabilityReceiver(get()) }
    single { RecentLocationStore() }
}
