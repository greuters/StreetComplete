package de.westnordost.streetcompletegpx.quests.incline_direction

import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.quests.incline_direction.Incline.*

enum class Incline {
    UP, UP_REVERSED
}

fun Incline.applyTo(tags: Tags) {
    tags["incline"] = when (this) {
        UP -> "up"
        UP_REVERSED -> "down"
    }
}
