package de.westnordost.streetcompletegpx.data.overlays

import android.content.SharedPreferences
import androidx.core.content.edit
import de.westnordost.streetcompletegpx.Prefs

class SelectedOverlayStore(private val prefs: SharedPreferences) {

    fun get(): String? = prefs.getString(Prefs.SELECTED_OVERLAY, null)

    fun set(value: String?) {
        prefs.edit { putString(Prefs.SELECTED_OVERLAY, value) }
    }
}
