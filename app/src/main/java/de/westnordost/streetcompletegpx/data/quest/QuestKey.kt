package de.westnordost.streetcompletegpx.data.quest

import de.westnordost.streetcompletegpx.data.osm.mapdata.ElementType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class QuestKey

@Serializable
@SerialName("osmnote")
data class OsmNoteQuestKey(val noteId: Long) : QuestKey()

@Serializable
@SerialName("osm")
data class OsmQuestKey(
    val elementType: ElementType,
    val elementId: Long,
    val questTypeName: String
) : QuestKey()
