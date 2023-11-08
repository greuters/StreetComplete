package de.westnordost.streetcompletegpx.data.osm.created_elements

import de.westnordost.streetcompletegpx.data.osm.mapdata.ElementType

interface CreatedElementsSource {
    /** Returns whether the given element has been created by this app */
    fun contains(elementType: ElementType, elementId: Long): Boolean
}
