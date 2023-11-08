package de.westnordost.streetcompletegpx.data.osm.edits

import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry

interface AddElementEditsController {
    fun add(
        type: ElementEditType,
        geometry: ElementGeometry,
        source: String,
        action: ElementEditAction
    )
}
