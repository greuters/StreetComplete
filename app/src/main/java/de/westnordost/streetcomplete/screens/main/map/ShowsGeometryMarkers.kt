package de.westnordost.streetcompletegpx.screens.main.map

import androidx.annotation.DrawableRes
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry

interface ShowsGeometryMarkers {
    fun putMarkerForCurrentHighlighting(
        geometry: ElementGeometry,
        @DrawableRes drawableResId: Int?,
        title: String?
    )
    fun deleteMarkerForCurrentHighlighting(geometry: ElementGeometry)

    fun clearMarkersForCurrentHighlighting()
}
