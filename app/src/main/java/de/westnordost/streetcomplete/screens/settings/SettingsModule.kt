package de.westnordost.streetcompletegpx.screens.settings

import org.koin.dsl.module

val settingsModule = module {
    single { ResurveyIntervalsUpdater(get()) }
}
