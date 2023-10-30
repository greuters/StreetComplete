package de.westnordost.streetcompletegpx.data.osm.osmquests

import de.westnordost.streetcompletegpx.data.osm.mapdata.ElementType
import de.westnordost.streetcompletegpx.data.osm.mapdata.LatLon
import de.westnordost.streetcompletegpx.data.quest.OsmQuestKey

interface OsmQuestDaoEntry {
    val questTypeName: String
    val elementType: ElementType
    val elementId: Long
    val position: LatLon
}

val OsmQuestDaoEntry.key get() =
    OsmQuestKey(elementType, elementId, questTypeName)
