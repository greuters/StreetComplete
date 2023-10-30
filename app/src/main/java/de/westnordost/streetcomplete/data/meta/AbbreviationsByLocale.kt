package de.westnordost.streetcompletegpx.data.meta

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.util.ktx.getYamlStringMap
import java.util.Locale

class AbbreviationsByLocale(private val applicationContext: Context) {
    private val byLanguageAbbreviations = HashMap<String, Abbreviations>()

    operator fun get(locale: Locale): Abbreviations? {
        val code = locale.toString()
        if (!byLanguageAbbreviations.containsKey(code)) {
            byLanguageAbbreviations[code] = load(locale)
        }
        return byLanguageAbbreviations[code]
    }

    private fun load(locale: Locale): Abbreviations {
        val config = getResources(locale).getYamlStringMap(R.raw.abbreviations)
        return Abbreviations(config, locale)
    }

    private fun getResources(locale: Locale): Resources {
        val configuration = Configuration(applicationContext.resources.configuration)
        configuration.setLocale(locale)
        return applicationContext.createConfigurationContext(configuration).resources
    }
}
