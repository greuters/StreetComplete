package de.westnordost.streetcompletegpx.data.overlays

import de.westnordost.streetcompletegpx.overlays.Overlay

interface SelectedOverlaySource {
    interface Listener {
        fun onSelectedOverlayChanged()
    }

    val selectedOverlay: Overlay?

    fun addListener(listener: Listener)
    fun removeListener(listener: Listener)
}
