package de.westnordost.streetcompletegpx.util.ktx

import android.location.Location
import de.westnordost.streetcompletegpx.data.osm.mapdata.LatLon

fun Location.toLatLon() = LatLon(latitude, longitude)
