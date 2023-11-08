package de.westnordost.streetcompletegpx.data.edithistory

import de.westnordost.streetcompletegpx.data.osm.mapdata.LatLon
import de.westnordost.streetcompletegpx.data.quest.OsmNoteQuestKey
import de.westnordost.streetcompletegpx.data.quest.OsmQuestKey

interface Edit {
    val key: EditKey
    val createdTimestamp: Long
    val isUndoable: Boolean
    val position: LatLon
    val isSynced: Boolean?
}

sealed class EditKey

data class ElementEditKey(val id: Long) : EditKey()
data class NoteEditKey(val id: Long) : EditKey()
data class OsmQuestHiddenKey(val osmQuestKey: OsmQuestKey) : EditKey()
data class OsmNoteQuestHiddenKey(val osmNoteQuestKey: OsmNoteQuestKey) : EditKey()
