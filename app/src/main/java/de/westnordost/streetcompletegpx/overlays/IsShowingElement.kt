package de.westnordost.streetcompletegpx.overlays

import de.westnordost.streetcompletegpx.data.osm.mapdata.ElementKey

interface IsShowingElement {
    /** The element that is showing right now, if any */
    val elementKey: ElementKey?
}
