package de.westnordost.streetcompletegpx.data.quest

import org.koin.dsl.module

val questModule = module {
    single { QuestAutoSyncer(get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    single { VisibleQuestsSource(get(), get(), get(), get(), get(), get()) }
}
