package de.westnordost.streetcompletegpx.quests.smoothness

import de.westnordost.streetcompletegpx.osm.Tags
import de.westnordost.streetcompletegpx.osm.changeToSteps
import de.westnordost.streetcompletegpx.osm.removeCheckDatesForKey
import de.westnordost.streetcompletegpx.osm.updateWithCheckDate

sealed interface SmoothnessAnswer

data class SmoothnessValueAnswer(val value: Smoothness) : SmoothnessAnswer

object IsActuallyStepsAnswer : SmoothnessAnswer
object WrongSurfaceAnswer : SmoothnessAnswer

fun SmoothnessAnswer.applyTo(tags: Tags) {
    tags.remove("smoothness:date")
    // similar tag as smoothness, will be wrong/outdated when smoothness is set
    tags.remove("surface:grade")
    when (this) {
        is SmoothnessValueAnswer -> {
            tags.updateWithCheckDate("smoothness", value.osmValue)
        }
        is WrongSurfaceAnswer -> {
            tags.remove("surface")
            tags.remove("smoothness")
            tags.removeCheckDatesForKey("smoothness")
        }
        is IsActuallyStepsAnswer -> {
            tags.changeToSteps()
        }
    }
}
