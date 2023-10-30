package de.westnordost.streetcompletegpx.data.osm.osmquests

import de.westnordost.streetcompletegpx.data.quest.OsmQuestKey

interface HideOsmQuestController {
    fun hide(key: OsmQuestKey)
}
