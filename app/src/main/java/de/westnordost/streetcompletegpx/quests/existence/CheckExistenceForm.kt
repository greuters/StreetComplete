package de.westnordost.streetcompletegpx.quests.existence

import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.quests.AbstractOsmQuestForm
import de.westnordost.streetcompletegpx.quests.AnswerItem

class CheckExistenceForm : AbstractOsmQuestForm<Unit>() {

    override val buttonPanelAnswers = listOf(
        AnswerItem(R.string.quest_generic_hasFeature_no) { deletePoiNode() },
        AnswerItem(R.string.quest_generic_hasFeature_yes) { applyAnswer(Unit) }
    )
}
