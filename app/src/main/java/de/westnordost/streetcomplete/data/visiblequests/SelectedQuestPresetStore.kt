package de.westnordost.streetcompletegpx.data.visiblequests

import android.content.SharedPreferences
import androidx.core.content.edit
import de.westnordost.streetcompletegpx.Prefs

class SelectedQuestPresetStore(private val prefs: SharedPreferences) {

    fun get(): Long = prefs.getLong(Prefs.SELECTED_QUESTS_PRESET, 0)

    fun set(value: Long) {
        prefs.edit { putLong(Prefs.SELECTED_QUESTS_PRESET, value) }
    }
}
