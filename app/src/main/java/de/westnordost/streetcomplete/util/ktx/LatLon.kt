package de.westnordost.streetcompletegpx.util.ktx

import de.westnordost.streetcompletegpx.data.osm.mapdata.LatLon
import kotlin.math.abs

/** OSM has limited precision of 7 decimals */
fun LatLon.equalsInOsm(other: LatLon) =
    !latitude.isDifferent(other.latitude, 1e-7)
    && !longitude.isDifferent(other.longitude, 1e-7)

private fun Double.isDifferent(other: Double, delta: Double) = abs(this - other) >= delta
