package de.westnordost.streetcompletegpx.osm.sidewalk_surface

import de.westnordost.streetcompletegpx.osm.surface.SurfaceAndNote

data class LeftAndRightSidewalkSurface(
    val left: SurfaceAndNote?,
    val right: SurfaceAndNote?,
)
