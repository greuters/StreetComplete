package de.westnordost.streetcompletegpx.quests.construction

import de.westnordost.streetcompletegpx.osm.SURVEY_MARK_KEY
import de.westnordost.streetcompletegpx.osm.Tags

fun removeTagsDescribingConstruction(tags: Tags) {
    tags.remove("construction")
    tags.remove("source:construction")
    tags.remove("opening_date")
    tags.remove("source:opening_date")
    tags.remove(SURVEY_MARK_KEY)
    tags.remove("source:$SURVEY_MARK_KEY")
}
