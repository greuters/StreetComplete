package de.westnordost.streetcompletegpx.data.osmnotes.notequests

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.quest.QuestType
import de.westnordost.streetcompletegpx.data.user.achievements.EditTypeAchievement
import de.westnordost.streetcompletegpx.quests.note_discussion.NoteDiscussionForm

object OsmNoteQuestType : QuestType {
    override val icon = R.drawable.ic_quest_notes
    override val title = R.string.quest_noteDiscussion_title
    override val wikiLink = "Notes"
    override val achievements = emptyList<EditTypeAchievement>()

    override fun createForm() = NoteDiscussionForm()
}
