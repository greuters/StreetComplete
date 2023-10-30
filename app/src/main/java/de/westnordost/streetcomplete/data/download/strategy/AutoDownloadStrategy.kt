package de.westnordost.streetcompletegpx.data.download.strategy

import de.westnordost.streetcompletegpx.data.osm.mapdata.BoundingBox
import de.westnordost.streetcompletegpx.data.osm.mapdata.LatLon

interface AutoDownloadStrategy {
    /** returns the bbox that should be downloaded at this position or null if nothing should be
     *  downloaded now */
    suspend fun getDownloadBoundingBox(pos: LatLon): BoundingBox?
}
