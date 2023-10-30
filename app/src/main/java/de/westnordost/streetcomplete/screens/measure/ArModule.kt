package de.westnordost.streetcompletegpx.screens.measure

import org.koin.dsl.module

val arModule = module {
    factory { ArSupportChecker(get()) }
}
