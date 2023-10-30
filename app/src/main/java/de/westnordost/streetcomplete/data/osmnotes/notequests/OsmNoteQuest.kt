package de.westnordost.streetcompletegpx.data.osmnotes.notequests

import de.westnordost.streetcompletegpx.data.osm.geometry.ElementGeometry
import de.westnordost.streetcompletegpx.data.osm.geometry.ElementPointGeometry
import de.westnordost.streetcompletegpx.data.osm.mapdata.LatLon
import de.westnordost.streetcompletegpx.data.quest.OsmNoteQuestKey
import de.westnordost.streetcompletegpx.data.quest.Quest
import de.westnordost.streetcompletegpx.data.quest.QuestType

/** Represents one task for the user to contribute to a public OSM note */
data class OsmNoteQuest(
    val id: Long,
    override val position: LatLon
) : Quest {
    override val type: QuestType get() = OsmNoteQuestType
    override val key: OsmNoteQuestKey by lazy { OsmNoteQuestKey(id) }
    override val markerLocations: Collection<LatLon> by lazy { listOf(position) }
    override val geometry: ElementGeometry get() = ElementPointGeometry(position)
}
