package de.westnordost.streetcompletegpx.screens.settings

import android.content.SharedPreferences
import de.westnordost.streetcompletegpx.Prefs
import de.westnordost.streetcompletegpx.Prefs.ResurveyIntervals.DEFAULT
import de.westnordost.streetcompletegpx.Prefs.ResurveyIntervals.LESS_OFTEN
import de.westnordost.streetcompletegpx.Prefs.ResurveyIntervals.MORE_OFTEN
import de.westnordost.streetcompletegpx.Prefs.ResurveyIntervals.valueOf
import de.westnordost.streetcompletegpx.data.elementfilter.filters.RelativeDate

/** This class is just to access the user's preference about which multiplier for the resurvey
 *  intervals to use */
class ResurveyIntervalsUpdater(private val prefs: SharedPreferences) {
    fun update() {
        RelativeDate.MULTIPLIER = multiplier
    }

    private val multiplier: Float get() = when (intervalsPreference) {
        LESS_OFTEN -> 2.0f
        DEFAULT -> 1.0f
        MORE_OFTEN -> 0.5f
    }

    private val intervalsPreference: Prefs.ResurveyIntervals get() =
        valueOf(prefs.getString(Prefs.RESURVEY_INTERVALS, "DEFAULT")!!)
}
