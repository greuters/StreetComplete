package de.westnordost.streetcompletegpx.data.osm.edits.upload

import android.content.SharedPreferences
import androidx.core.content.edit
import de.westnordost.streetcompletegpx.Prefs
import de.westnordost.streetcompletegpx.util.ktx.nowAsEpochMilliseconds

class LastEditTimeStore(private val prefs: SharedPreferences) {

    fun touch() {
        prefs.edit { putLong(Prefs.LAST_EDIT_TIME, nowAsEpochMilliseconds()) }
    }

    fun get(): Long =
        prefs.getLong(Prefs.LAST_EDIT_TIME, 0)
}
