package de.westnordost.streetcompletegpx.screens.main.bottom_sheet

import de.westnordost.streetcompletegpx.data.osm.mapdata.LatLon

interface IsMapPositionAware {
    fun onMapMoved(position: LatLon)
}
