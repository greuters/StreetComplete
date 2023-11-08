package de.westnordost.streetcompletegpx.screens.settings.questselection

import de.westnordost.streetcompletegpx.data.osmnotes.notequests.OsmNoteQuestType
import de.westnordost.streetcompletegpx.data.quest.QuestType

data class QuestVisibility(val questType: QuestType, var visible: Boolean) {
    val isInteractionEnabled get() = questType !is OsmNoteQuestType
}
