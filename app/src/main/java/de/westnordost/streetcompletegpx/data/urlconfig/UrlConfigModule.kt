package de.westnordost.streetcompletegpx.data.urlconfig

import org.koin.dsl.module

val urlConfigModule = module {
    single { UrlConfigController(get(), get(), get(), get(), get(), get()) }
}
