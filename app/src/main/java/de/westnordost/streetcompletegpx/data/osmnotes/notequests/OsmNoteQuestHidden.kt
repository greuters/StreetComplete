package de.westnordost.streetcompletegpx.data.osmnotes.notequests

import de.westnordost.streetcompletegpx.data.edithistory.Edit
import de.westnordost.streetcompletegpx.data.edithistory.OsmNoteQuestHiddenKey
import de.westnordost.streetcompletegpx.data.osm.mapdata.LatLon
import de.westnordost.streetcompletegpx.data.osmnotes.Note
import de.westnordost.streetcompletegpx.data.quest.OsmNoteQuestKey

data class OsmNoteQuestHidden(
    val note: Note,
    override val createdTimestamp: Long
) : Edit {
    override val key: OsmNoteQuestHiddenKey get() = OsmNoteQuestHiddenKey(OsmNoteQuestKey(note.id))
    override val isUndoable: Boolean get() = true
    override val position: LatLon get() = note.position
    override val isSynced: Boolean? get() = null
}
