package de.westnordost.streetcompletegpx.screens.main.map

import android.content.res.Resources
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import de.westnordost.streetcompletegpx.data.edithistory.Edit
import de.westnordost.streetcompletegpx.data.edithistory.EditHistorySource
import de.westnordost.streetcompletegpx.data.edithistory.EditKey
import de.westnordost.streetcompletegpx.data.edithistory.ElementEditKey
import de.westnordost.streetcompletegpx.data.edithistory.NoteEditKey
import de.westnordost.streetcompletegpx.data.edithistory.OsmNoteQuestHiddenKey
import de.westnordost.streetcompletegpx.data.edithistory.OsmQuestHiddenKey
import de.westnordost.streetcompletegpx.data.edithistory.icon
import de.westnordost.streetcompletegpx.data.osm.edits.ElementEdit
import de.westnordost.streetcompletegpx.data.osm.mapdata.ElementType
import de.westnordost.streetcompletegpx.data.osm.osmquests.OsmQuestHidden
import de.westnordost.streetcompletegpx.data.osmnotes.edits.NoteEdit
import de.westnordost.streetcompletegpx.data.osmnotes.notequests.OsmNoteQuestHidden
import de.westnordost.streetcompletegpx.data.quest.OsmNoteQuestKey
import de.westnordost.streetcompletegpx.data.quest.OsmQuestKey
import de.westnordost.streetcompletegpx.screens.main.map.components.Pin
import de.westnordost.streetcompletegpx.screens.main.map.components.PinsMapComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditHistoryPinsManager(
    private val pinsMapComponent: PinsMapComponent,
    private val editHistorySource: EditHistorySource,
    private val resources: Resources
) : DefaultLifecycleObserver {

    private val viewLifecycleScope: CoroutineScope = CoroutineScope(SupervisorJob())

    /** Switch visibility of edit history pins layer */
    var isVisible: Boolean = false
        set(value) {
            if (field == value) return
            field = value
            if (value) show() else hide()
        }

    private var isStarted: Boolean = false

    private val editHistoryListener = object : EditHistorySource.Listener {
        override fun onAdded(edit: Edit) { updatePins() }
        override fun onSynced(edit: Edit) {}
        override fun onDeleted(edits: List<Edit>) { updatePins() }
        override fun onInvalidated() { updatePins() }
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        isStarted = true
        show()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        isStarted = false
        hide()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        viewLifecycleScope.cancel()
    }

    private fun show() {
        if (!isStarted || !isVisible) return
        updatePins()
        editHistorySource.addListener(editHistoryListener)
    }

    private fun hide() {
        viewLifecycleScope.coroutineContext.cancelChildren()
        viewLifecycleScope.launch { pinsMapComponent.clear() }
        editHistorySource.removeListener(editHistoryListener)
    }

    fun getEditKey(properties: Map<String, String>): EditKey? =
        properties.toEditKey()

    private fun updatePins() {
        viewLifecycleScope.launch {
            if (this@EditHistoryPinsManager.isVisible) {
                val edits = withContext(Dispatchers.IO) { editHistorySource.getAll() }
                val pins = createEditPins(edits)
                pinsMapComponent.set(pins)
            }
        }
    }

    private fun createEditPins(edits: List<Edit>): List<Pin> =
        edits.mapIndexed { index, edit ->
            Pin(
                edit.position,
                resources.getResourceEntryName(edit.icon),
                edit.toProperties(),
                edits.size - index // most recent first
            )
        }
}

private const val MARKER_EDIT_TYPE = "edit_type"

private const val MARKER_ELEMENT_TYPE = "element_type"
private const val MARKER_ELEMENT_ID = "element_id"
private const val MARKER_QUEST_TYPE = "quest_type"
private const val MARKER_NOTE_ID = "note_id"
private const val MARKER_ID = "id"

private const val EDIT_TYPE_ELEMENT = "element"
private const val EDIT_TYPE_NOTE = "note"
private const val EDIT_TYPE_HIDE_OSM_NOTE_QUEST = "hide_osm_note_quest"
private const val EDIT_TYPE_HIDE_OSM_QUEST = "hide_osm_quest"

private fun Edit.toProperties(): List<Pair<String, String>> = when (this) {
    is ElementEdit -> listOf(
        MARKER_EDIT_TYPE to EDIT_TYPE_ELEMENT,
        MARKER_ID to id.toString()
    )
    is NoteEdit -> listOf(
        MARKER_EDIT_TYPE to EDIT_TYPE_NOTE,
        MARKER_ID to id.toString()
    )
    is OsmNoteQuestHidden -> listOf(
        MARKER_EDIT_TYPE to EDIT_TYPE_HIDE_OSM_NOTE_QUEST,
        MARKER_NOTE_ID to note.id.toString()
    )
    is OsmQuestHidden -> listOf(
        MARKER_EDIT_TYPE to EDIT_TYPE_HIDE_OSM_QUEST,
        MARKER_ELEMENT_TYPE to elementType.name,
        MARKER_ELEMENT_ID to elementId.toString(),
        MARKER_QUEST_TYPE to questType.name
    )
    else -> throw IllegalArgumentException()
}

private fun Map<String, String>.toEditKey(): EditKey? = when (get(MARKER_EDIT_TYPE)) {
    EDIT_TYPE_ELEMENT ->
        ElementEditKey(getValue(MARKER_ID).toLong())
    EDIT_TYPE_NOTE ->
        NoteEditKey(getValue(MARKER_ID).toLong())
    EDIT_TYPE_HIDE_OSM_QUEST ->
        OsmQuestHiddenKey(OsmQuestKey(
            getValue(MARKER_ELEMENT_TYPE).let { ElementType.valueOf(it) },
            getValue(MARKER_ELEMENT_ID).toLong(),
            getValue(MARKER_QUEST_TYPE)
        ))
    EDIT_TYPE_HIDE_OSM_NOTE_QUEST ->
        OsmNoteQuestHiddenKey(OsmNoteQuestKey(getValue(MARKER_NOTE_ID).toLong()))
    else -> null
}
