package de.westnordost.streetcompletegpx.osm.sidewalk_surface

import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.osm.expandSides
import de.westnordost.streetcompletegpx.osm.hasCheckDateForKey
import de.westnordost.streetcompletegpx.osm.mergeSides
import de.westnordost.streetcompletegpx.osm.surface.applyTo
import de.westnordost.streetcompletegpx.osm.updateCheckDateForKey

fun LeftAndRightSidewalkSurface.applyTo(tags: Tags) {
    tags.expandSides("sidewalk", "surface")
    tags.expandSides("sidewalk", "surface:note")
    tags.expandSides("sidewalk", "smoothness")

    left?.applyTo(tags, "sidewalk:left", updateCheckDate = false)
    right?.applyTo(tags, "sidewalk:right", updateCheckDate = false)

    tags.mergeSides("sidewalk", "surface")
    tags.mergeSides("sidewalk", "surface:note")
    tags.mergeSides("sidewalk", "smoothness")

    if (!tags.hasChanges || tags.hasCheckDateForKey("sidewalk:surface")) {
        tags.updateCheckDateForKey("sidewalk:surface")
    }
}
