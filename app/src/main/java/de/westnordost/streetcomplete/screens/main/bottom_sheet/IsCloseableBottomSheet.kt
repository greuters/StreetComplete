package de.westnordost.streetcompletegpx.screens.main.bottom_sheet

import de.westnordost.streetcompletegpx.data.osm.mapdata.LatLon

interface IsCloseableBottomSheet {
    /** Returns true if the bottom sheet shall consume the event */
    fun onClickMapAt(position: LatLon, clickAreaSizeInMeters: Double): Boolean
    fun onClickClose(onConfirmed: () -> Unit)
}
