package de.westnordost.streetcompletegpx.data.upload

import de.westnordost.streetcompletegpx.data.osm.mapdata.LatLon

interface OnUploadedChangeListener {
    fun onUploaded(questType: String, at: LatLon)
    fun onDiscarded(questType: String, at: LatLon)
}
