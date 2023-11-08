package de.westnordost.streetcompletegpx.data.osm.osmquests

import de.westnordost.streetcompletegpx.data.edithistory.Edit
import de.westnordost.streetcompletegpx.data.edithistory.OsmQuestHiddenKey
import de.westnordost.streetcompletegpx.data.osm.mapdata.ElementType
import de.westnordost.streetcompletegpx.data.osm.mapdata.LatLon
import de.westnordost.streetcompletegpx.data.quest.OsmQuestKey

data class OsmQuestHidden(
    val elementType: ElementType,
    val elementId: Long,
    val questType: OsmElementQuestType<*>,
    override val position: LatLon,
    override val createdTimestamp: Long
) : Edit {
    val questKey get() = OsmQuestKey(elementType, elementId, questType.name)
    override val key: OsmQuestHiddenKey get() = OsmQuestHiddenKey(questKey)
    override val isUndoable: Boolean get() = true
    override val isSynced: Boolean? get() = null
}
